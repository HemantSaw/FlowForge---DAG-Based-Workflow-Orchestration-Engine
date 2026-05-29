function TaskCard({ task, onDelete }) {

    return (

        <div className="bg-white rounded-lg shadow-sm p-4">

            <h3
                className="
                    text-lg
                    font-semibold
                "
            >
                {task.name}
            </h3>

            <p>
                Status:
                <span className="ml-2">
                    {task.status}
                </span>
            </p>

            <p>
                Type:
                <span className="ml-2">
                    {task.taskType}
                </span>
            </p>

            <div className="mt-2">

                <p className="font-semibold">
                    Depends On:
                </p>

                {
                    task.dependsOn.length === 0
                    ? (
                        <p>No Dependencies</p>
                    )
                    : (
                        task.dependsOn.map(dep => (
                            <p key={dep.id}>
                                • {dep.name}
                            </p>
                        ))
                    )
                }

            </div>

            <button
                onClick={() =>
                    onDelete(task.id)
                }
                className="
                    bg-red-500
                    text-white
                    px-3
                    py-1
                    rounded
                    mt-2
                "
            >
                Delete
            </button>

        </div>

    );
}

export default TaskCard;