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

package eu.esdihumboldt.hale.models.task;

import eu.esdihumboldt.hale.models.AbstractUpdateService;
import eu.esdihumboldt.hale.models.HaleServiceListener;
import eu.esdihumboldt.hale.models.TaskService;
import eu.esdihumboldt.hale.models.UpdateMessage;
import eu.esdihumboldt.hale.task.ResolvedTask;
import eu.esdihumboldt.hale.task.Task;

/**
 * Notification handling for {@link TaskService}s that support
 * {@link TaskServiceListener}s
 *
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$ 
 */
public abstract class AbstractTaskService extends AbstractUpdateService
		implements TaskService {

	/**
	 * The default update message
	 */
	private static final UpdateMessage<?> DEF_MESSAGE = new UpdateMessage<Object>(TaskService.class, null);
	
	/**
	 * @see AbstractUpdateService#notifyListeners(UpdateMessage)
	 * @deprecated an {@link UnsupportedOperationException} will be thrown,
	 *   use another notification method instead
	 */
	@Deprecated
	@Override
	protected void notifyListeners(UpdateMessage<?> message) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Call when tasks have been added
	 * 
	 * @param tasks the tasks that have been added
	 */
	protected void notifyTasksAdded(Iterable<Task> tasks) {
		for (HaleServiceListener listener : getListeners()) {
			if (listener instanceof TaskServiceListener) {
				((TaskServiceListener) listener).tasksAdded(tasks);
			}
			
			listener.update(DEF_MESSAGE);
		}
	}
	
	/**
	 * Call when tasks have been removed
	 * 
	 * @param tasks the tasks that have been removed
	 */
	protected void notifyTasksRemoved(Iterable<Task> tasks) {
		for (HaleServiceListener listener : getListeners()) {
			if (listener instanceof TaskServiceListener) {
				((TaskServiceListener) listener).tasksRemoved(tasks);
			}
			
			listener.update(DEF_MESSAGE);
		}
	}
	
	/**
	 * Call when the user data of a task has changed
	 * 
	 * @param task the resolved task
	 */
	protected void notifyTaskUserDataChanged(ResolvedTask task) {
		for (HaleServiceListener listener : getListeners()) {
			if (listener instanceof TaskServiceListener) {
				((TaskServiceListener) listener).taskUserDataChanged(task);
			}
			
			listener.update(DEF_MESSAGE);
		}
	}

}