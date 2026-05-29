function TaskExecutionCard({
    taskExecution
}) {

    return (

        <div
            className="
                bg-white
                rounded-lg
                shadow-sm
                p-4
            "
        >

            <h3 className="font-semibold">

                {
                    taskExecution.task.name
                }

            </h3>

            <p>
                Status:
                {" "}
                {
                    taskExecution.status
                }
            </p>

            <p>
                Retry Count:
                {" "}
                {
                    taskExecution.retryCount
                }
            </p>

            <p>
                Started:
                {" "}
                {
                    taskExecution.startedAt
                }
            </p>

            <p>
                Completed:
                {" "}
                {
                    taskExecution.completedAt
                }
            </p>

        </div>

    );
}

export default TaskExecutionCard;