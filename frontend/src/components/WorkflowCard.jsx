import { Link } from "react-router-dom";

function WorkflowCard({workflow, handleDelete}) {
  return (
    <div className="bg-white p-6 rounded-lg shadow-md">

      <h2 className="text-xl font-bold">
        {workflow.name}
      </h2>
      <div className="mt-3 space-y-1">
        <p>
          Status: 
          <span className="font-semibold ml-2">{workflow.status}</span>
        </p>

        <p>
          Tasks: 
          <span className="font-semibold ml-2">{workflow.tasks?.length}</span>
        </p>
        <p>
          Created by: 
          <span className="font-semibold ml-2">{workflow?.user?.username}</span>
          </p>
      </div>
      <div className="flex gap-2">
        <Link
            to={`/workflows/${workflow.id}`}
            className="inline-block mt-4 bg-blue-500 text-white px-4 py-2 rounded-lg"
        >
            View Details
        </Link>
        <button
            onClick={() =>
                handleDelete(
                    workflow.id
                )
            }
            className="
                bg-red-500
                text-white
                px-3
                py-2
                rounded-lg
                mt-3
            "
        >
            Delete
        </button>
      </div>

    </div>
  );
}

export default WorkflowCard;