/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 */

package eu.esdihumboldt.hale.ui.io.legacy;

import java.io.File;
import java.net.URL;
import java.util.Set;

import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.ui.internal.Messages;
import eu.esdihumboldt.hale.ui.service.schema.SchemaService;
import eu.esdihumboldt.hale.ui.service.schema.SchemaService.SchemaType;

/**
 * 
 * This is the main page of the {@link SchemaImportWizard}.
 * 
 * @author Thorsten Reitz 
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 */
public class SchemaImportWizardMainPage 
	extends WizardPage {
	
	private static ALogger _log = ALoggerFactory.getLogger(SchemaImportWizardMainPage.class);
	
	private Composite ffe_container;
	private Composite ufe_container;
	private FileFieldEditor fileFieldEditor;
	private UrlFieldEditor wfsFieldEditor;
	private Button useWfsRadio;
	private Button useFileRadio;
	private Button sourceDestination;
	private Button targetDestination;
	
	/**
	 * Constructor
	 * 
	 * @param pageName the page name
	 * @param pageTitle the page title
	 */
	public SchemaImportWizardMainPage(String pageName, String pageTitle) {
		super(pageName, pageTitle, (ImageDescriptor) null); // FIXME ImageDescriptor
		setTitle(pageName); //NON-NLS-1
		setDescription(Messages.SchemaImportWizardMainPage_SchemaImportDescription); //NON-NLS-1
	}

	/**
	 * The parent methods where all controls are created for this {@link WizardPage}.
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		
        super.initializeDialogUnits(parent);
        this.setPageComplete(this.isPageComplete());
        
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        composite.setFont(parent.getFont());

        this.createDestinationGroup(composite);
        this.createSourceGroup(composite);
        
        setErrorMessage(null);	// should not initially have error message
		super.setControl(composite);
	}
	

	/**
	 * Creates the UI controls for the selection of the source of the schema
	 * to be imported.
	 * 
	 * @param parent the parent {@link Composite}
	 */
	private void createSourceGroup(Composite parent) {
		
		// define source group composite
		Group selectionArea = new Group(parent, SWT.NONE);
		selectionArea.setText(Messages.SchemaImportWizardMainPage_SchemaImportReadSchema);
		selectionArea.setLayout(new GridLayout());
		GridData selectionAreaGD = new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL);
		selectionAreaGD.grabExcessHorizontalSpace = true;
		selectionArea.setLayoutData(selectionAreaGD);
		selectionArea.setSize(selectionArea.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		selectionArea.setFont(parent.getFont());
		
		// read from file (XSD, GML, XML)
		final Composite fileSelectionArea = new Composite(selectionArea, SWT.NONE);
		GridData fileSelectionData = new GridData(
				GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL);
		fileSelectionData.grabExcessHorizontalSpace = true;
		fileSelectionArea.setLayoutData(fileSelectionData);

		GridLayout fileSelectionLayout = new GridLayout();
		fileSelectionLayout.numColumns = 2;
		fileSelectionLayout.makeColumnsEqualWidth = false;
		fileSelectionLayout.marginWidth = 0;
		fileSelectionLayout.marginHeight = 0;
		fileSelectionArea.setLayout(fileSelectionLayout);
		this.useFileRadio = new Button(fileSelectionArea, SWT.RADIO);
		useFileRadio.setSelection(true);
		this.ffe_container = new Composite(fileSelectionArea, SWT.NULL);
		ffe_container.setLayoutData(
				new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		fileFieldEditor = new FileFieldEditor("fileSelect",  //$NON-NLS-1$
				Messages.SchemaImportWizardMainPage_File, ffe_container);
		fileFieldEditor.getTextControl(ffe_container).addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				getWizard().getContainer().updateButtons();
			}
		});
		
		SchemaService ss = (SchemaService) PlatformUI.getWorkbench().getService(SchemaService.class);
		Set<String> formats = ss.getSupportedSchemaFormats();
		
		String[] extensions = InstanceDataImportWizardMainPage.buildExtensions(formats);
		fileFieldEditor.setFileExtensions(extensions);
		
		// read from WFS (DescribeFeatureType)
		this.useWfsRadio = new Button(fileSelectionArea, SWT.RADIO);
		this.ufe_container = new Composite(fileSelectionArea, SWT.NULL);
		ufe_container.setLayoutData(
				new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		wfsFieldEditor = new UrlFieldEditor("urlSelect",  //$NON-NLS-1$
				Messages.SchemaImportWizardMainPage_1, ufe_container); //$NON-NLS-1$
		wfsFieldEditor.setEnabled(false, ufe_container);
		wfsFieldEditor.getTextControl(ufe_container).addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				getWizard().getContainer().updateButtons();
			}
		});
		
		// add listeners to radio buttons
		useFileRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.widget).getSelection()) {
					fileFieldEditor.setEnabled(true, ffe_container);
					wfsFieldEditor.setEnabled(false, ufe_container);
				}
				else {
					fileFieldEditor.setEnabled(false, ffe_container);
					wfsFieldEditor.setEnabled(true, ufe_container);
				}
			}
		});
		
		useWfsRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.widget).getSelection()) {
					fileFieldEditor.setEnabled(false, ffe_container);
					wfsFieldEditor.setEnabled(true, ufe_container);
				}
				else {
					fileFieldEditor.setEnabled(true, ffe_container);
					wfsFieldEditor.setEnabled(false, ufe_container);
				}
			}
		});
		
		// finish some stuff.
		fileSelectionArea.moveAbove(null);
		
	}

	/**
	 * creates the UI controls for the selection of the place where to import 
	 * the schema to (target schema or source schema)
	 * @param parent the parent {@link Composite}
	 */
	private void createDestinationGroup(Composite parent) {
		// define source group composite
		Group destinationArea = new Group(parent, SWT.NONE);
		destinationArea.setText(Messages.SchemaImportWizardMainPage_ImportDestination);
		destinationArea.setLayout(new GridLayout());
		GridData destinationAreaGD = new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL);
		destinationAreaGD.grabExcessHorizontalSpace = true;
		destinationArea.setLayoutData(destinationAreaGD);
		destinationArea.setSize(destinationArea.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		destinationArea.setFont(parent.getFont());
		
		sourceDestination = new Button(destinationArea, SWT.RADIO);
		sourceDestination.setSelection(true);
		sourceDestination.setText(Messages.SchemaImportWizardMainPage_ImportSource);
		
		targetDestination = new Button(destinationArea, SWT.RADIO);
		targetDestination.setText(Messages.SchemaImportWizardMainPage_ImportTarget);
	}
	
	/**
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		if (this.fileFieldEditor != null && this.wfsFieldEditor != null) {
			try {
				String test = this.getResult();
				if (this.useWfsRadio.getSelection()) {
					// test whether content of the WFS Field Editor validates to URL.
					if (test != null && !test.equals("")) {  //$NON-NLS-1$
						URL url = new URL(test);
						_log.info("wfsFieldEditor URL was OK: " + url.toString()); //$NON-NLS-1$
					} 
					else {
						return false;
					}
				}
				else {
					// test whether content of the File Field Editor validates to URI.
					if (test != null && !test.equals("")) { //$NON-NLS-1$
						File f = new File(test);
						_log.info("fileFieldEditor URI was OK: " + f.toURI()); //$NON-NLS-1$
					}
					else {
						return false;
					}
				}	
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			} 
			_log.debug("Page is complete."); //$NON-NLS-1$
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @return a String representing the URL or URI to load the schema from.
	 */
	public String getResult() {
		if (this.useWfsRadio.getSelection()) {
			return this.wfsFieldEditor.getStringValue();
		}
		else {
			return this.fileFieldEditor.getStringValue(); 
		}
	}
	
	/**
	 * Get the schema type
	 * 
	 * @return the schema type
	 */
	public SchemaType getSchemaType() {
		if (sourceDestination.getSelection()) {
			return SchemaType.SOURCE;
		}
		else return SchemaType.TARGET;
	}
	
	/**
	 * @return true if the user has selected to load from a WFS. 
	 */
	public boolean useWfs() {
		return this.useWfsRadio.getSelection();
	}
	
}