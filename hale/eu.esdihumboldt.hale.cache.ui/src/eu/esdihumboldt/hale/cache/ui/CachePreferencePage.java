package eu.esdihumboldt.hale.cache.ui;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.fhg.igd.osgi.util.OsgiUtils;
import de.fhg.igd.osgi.util.configuration.IConfigurationService;
import de.fhg.igd.osgi.util.configuration.JavaPreferencesConfigurationService;
import de.fhg.igd.osgi.util.configuration.NamespaceConfigurationServiceDecorator;
import eu.esdihumboldt.hale.cache.Request;

public class CachePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	private IConfigurationService org;
	private static final String DELIMITER = "/";
	private PreferenceStore prefs = new PreferenceStore();
	
	public CachePreferencePage() {
		super(GRID);
		setPreferenceStore(prefs);
		
		IConfigurationService org = OsgiUtils.getService(IConfigurationService.class);
		if (org == null) {
			// if no configuration service is present, fall back to new instance
			
			// 1. use user prefs, may not have rights to access system prefs
		    // 2. no default properties
		    // 3. don't default to system properties
			org = new JavaPreferencesConfigurationService(false, null, false);
		}
		
		this.org = new NamespaceConfigurationServiceDecorator(
				org, 
				Request.class.getPackage().getName().replace(".", DELIMITER),  //$NON-NLS-1$
				DELIMITER);
	}
	
	/**
	 * @see IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		//nothing to do here
	}

	@Override
	protected void createFieldEditors() {
		// restore data
		prefs.setValue("cache.enabled", org.getBoolean("cache.enabled", false));
		prefs.setValue("cache.path",	org.get("cache.path", Platform.getLocation().toString()));
		prefs.setValue("cache.name",	org.get("cache.name", "HALE_WebRequest"));
		
		// add fields
		addField(new BooleanFieldEditor("cache.enabled", "Activate caching", getFieldEditorParent()));
		addField(new DirectoryFieldEditor("cache.path", "Cache Path", getFieldEditorParent()));
		addField(new StringFieldEditor("cache.name", "Cache Name", 20, getFieldEditorParent()));
		
		// placeholder
//		Composite ph = new Composite(getFieldEditorParent(), SWT.NONE);
//		ph.setLayoutData(GridDataFactory.swtDefaults().hint(0, 0).create());
		
		Button clearCache = new Button(getFieldEditorParent(), GRID);
		clearCache.setText("Clear Cache");
		clearCache.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Request.getInstance().clear();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		clearCache.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 1, 1));
	}
	
	/**
	 * @see FieldEditorPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		org.setBoolean("cache.enabled", prefs.getBoolean("cache.enabled"));
		org.set("cache.path", prefs.getString("cache.path"));
		org.set("cache.name", prefs.getString("cache.name"));
		
		//
		Request.getInstance().setEnabled(prefs.getBoolean("cache.enabled"));

		return super.performOk();
	}
}