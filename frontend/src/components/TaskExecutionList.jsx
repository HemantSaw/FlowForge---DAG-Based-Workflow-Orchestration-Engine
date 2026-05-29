import TaskExecutionCard
from "./TaskExecutionCard";

function TaskExecutionList({taskExecutions}) {

    return (

        <div className="space-y-4">

            {
                taskExecutions.map(
                    taskExecution => (

                        <TaskExecutionCard
                            key={
                                taskExecution.id
                            }
                            taskExecution={
                                taskExecution
                            }
                        />

                    )
                )
            }

        </div>

    );
}

export default TaskExecutionList;