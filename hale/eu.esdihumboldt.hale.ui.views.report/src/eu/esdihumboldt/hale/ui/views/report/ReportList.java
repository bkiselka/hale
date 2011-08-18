/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2011.
 */

package eu.esdihumboldt.hale.ui.views.report;

import org.eclipse.jface.action.IMenuManager;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import swing2swt.layout.BorderLayout;
import eu.esdihumboldt.hale.core.io.project.ProjectInfo;
import eu.esdihumboldt.hale.core.io.project.model.Project;
import eu.esdihumboldt.hale.core.report.Message;
import eu.esdihumboldt.hale.core.report.Report;
import eu.esdihumboldt.hale.ui.service.project.ProjectService;
import eu.esdihumboldt.hale.ui.service.report.ReportListener;
import eu.esdihumboldt.hale.ui.service.report.ReportService;
import eu.esdihumboldt.hale.ui.views.report.properties.ReportPropertiesViewPart;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import eu.esdihumboldt.hale.core.report.writer.ReportWriter;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.ResourceManager;

/**
 * This is the Report view.
 * 
 * @author Andreas Burchert
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 */
public class ReportList extends ReportPropertiesViewPart implements ReportListener<Report<Message>, Message> {

	/**
	 * The ID for this plugin.
	 */
	public static final String ID = "eu.esdihumboldt.hale.ui.views.report.ReportList"; //$NON-NLS-1$
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private TreeViewer _treeViewer;

	/**
	 * Constructor.
	 */
	public ReportList() {
		// get ReportService and add listener
		ReportService repService = (ReportService) PlatformUI.getWorkbench().getService(ReportService.class);
		repService.addReportListener(this);
	}

	/**
	 * Create contents of the view part.
	 * @param parent parent element
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));
		{
			Composite composite = new Composite(container, SWT.NONE);
			composite.setLayoutData(BorderLayout.CENTER);
			formToolkit.adapt(composite);
			formToolkit.paintBordersFor(composite);
			composite.setLayout(new TreeColumnLayout());
			{
				_treeViewer = new TreeViewer(composite, SWT.BORDER);
				Tree tree = _treeViewer.getTree();
				tree.setHeaderVisible(true);
				tree.setLinesVisible(true);
				formToolkit.paintBordersFor(tree);
				
				Menu menu = new Menu(tree);
				tree.setMenu(menu);
				
				MenuItem mntmCopy = new MenuItem(menu, SWT.NONE);
				mntmCopy.setImage(ResourceManager.getPluginImage("eu.esdihumboldt.hale.ui.views.report", "icons/popupmenu/copy_edit.gif"));
				mntmCopy.setText("Copy");
				
				new MenuItem(menu, SWT.SEPARATOR);
				
				MenuItem mntmClearReportList = new MenuItem(menu, SWT.NONE);
				mntmClearReportList.setImage(ResourceManager.getPluginImage("eu.esdihumboldt.hale.ui.views.report", "icons/popupmenu/clear_co.gif"));
				mntmClearReportList.setText("Clear Report List");
				
				MenuItem mntmDeleteLog = new MenuItem(menu, SWT.NONE);
				mntmDeleteLog.setImage(ResourceManager.getPluginImage("eu.esdihumboldt.hale.ui.views.report", "icons/popupmenu/delete_obj.gif"));
				mntmDeleteLog.setText("Delete Log");
				
				MenuItem mntmRestoreLog = new MenuItem(menu, SWT.NONE);
				mntmRestoreLog.setImage(ResourceManager.getPluginImage("eu.esdihumboldt.hale.ui.views.report", "icons/popupmenu/restore_log.gif"));
				mntmRestoreLog.setText("Restore Log");
				
				new MenuItem(menu, SWT.SEPARATOR);
				
				MenuItem mntmExportLog = new MenuItem(menu, SWT.NONE);
				mntmExportLog.setText("Export Log");
				
				MenuItem mntmExportEntry = new MenuItem(menu, SWT.NONE);
				mntmExportEntry.setText("Export Entry");
			}
		}

		createActions();
		initializeToolBar();
		initializeMenu();
		
		// set label provider
		_treeViewer.setLabelProvider(new ReportListLabelProvider());
		
		// set content provider
		_treeViewer.setContentProvider(new ReportListContentProvider());
		
		// set selection provider
		getSite().setSelectionProvider(_treeViewer);
		
		// feed a dummy object so old reports are restored if this widget was disposed
		_treeViewer.setInput(new Object());
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	@SuppressWarnings("unused")
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	@SuppressWarnings("unused")
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
		_treeViewer.getControl().setFocus();
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.service.report.ReportListener#getReportType()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getReportType() {
		return Report.class;
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.service.report.ReportListener#getMessageType()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getMessageType() {
		return Message.class;
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.service.report.ReportListener#reportAdded(eu.esdihumboldt.hale.core.report.Report)
	 */
	@Override
	public void reportAdded(final Report<Message> report) {
		final ReportWriter reportWriter = new ReportWriter(); // remove this soon
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				try{
					
					ProjectService proService = (ProjectService) PlatformUI.getWorkbench().getService(ProjectService.class);
					ProjectInfo info = proService.getProjectInfo();
					
					if (info == null) {
						Project temp = new Project();
						temp.setName("Unknown");
						info = temp;
					}
					
					_treeViewer.setInput(new ReportItem(info, report));
					
					// remove this (test purpose)
					reportWriter.addReport(report);
					reportWriter.write();
					// until here
				} catch (NullPointerException e) {
					// TODO remove this or add proper Exception handling
				}
			}
		});
	}
}
