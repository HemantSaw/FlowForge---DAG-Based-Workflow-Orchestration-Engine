import { Link } from "react-router-dom";

function ExecutionCard({ execution }) {

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
                Execution #{execution.id}
            </h3>

            <p>
                Status:
                <span
                    className={`
                        ml-2
                        font-semibold
                        ${
                            execution.status === "SUCCESS"
                                ? "text-green-600"
                                : execution.status === "FAILED"
                                ? "text-red-600"
                                : "text-yellow-600"
                        }
                    `}
                >
                    {execution.status}
                </span>
            </p>

            <p>
                Started:
                <span className="ml-2">
                    {execution.startedAt}
                </span>
            </p>

            <Link
                to={`/task-executions/${execution.id}`}
                className="
                    inline-block
                    mt-3
                    bg-blue-500
                    text-white
                    px-3
                    py-2
                    rounded-lg
                "
            >
                View Details
            </Link>

        </div>

    );
}

export default ExecutionCard;