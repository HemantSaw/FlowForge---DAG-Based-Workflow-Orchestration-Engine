import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import TaskList from "../components/TaskList";
import WorkflowGraph from "../components/WorkflowGraph";
import ExecutionHistory from "../components/ExecutionHistory";
import CreateTaskModal from "../components/CreateTaskModal";
import AddDependencyModal from "../components/AddDependencyModal";

function WorkflowDetails() {
    const {id} = useParams();

    const [workflow, setWorkflow] = useState(null);
    const [executions, setExecutions] = useState([]);
    const [showCreateTaskModal, setShowCreateTaskModal] = useState(false);
    const [showAddDependencyModal, setShowAddDependencyModal] = useState(false);

    const [loading, setLoading] = useState(true);
    const [executing, setExecuting] = useState(false);

    const hasTasks = workflow?.tasks?.length >0;

    useEffect(()=>{
        getWorkflowDetail();
        fetchExecutions();
    }, [id]);

    const handleTaskCreated =
        (newTask) => {

        setWorkflow({

            ...workflow,

            tasks: [

                ...workflow.tasks,

                newTask

            ]

        });

    };
    const handleDependencyAdded = async () => {
        await getWorkflowDetail();
    };
    const handleDeleteTask =
        async (taskId) => {

        try {

            await axios.delete(
                `http://localhost:8080/tasks/delete/${taskId}`
            );

            await getWorkflowDetail();

        } catch(error) {

            console.error(error);
        }
    };
    
    const getWorkflowDetail = async()=>{
        try{
            const response = await axios.get(`http://127.0.0.1:8080/workflows/get-workflow-by-workflowId/${id}`)
    
            setWorkflow(response.data);
        }
        catch(error){
            console.log(error);
        }
        finally{
            setLoading(false);
        }

    }
    const executeWorkflow = async () => {

        try {
            setExecuting(true);

            await axios.post(
                `http://localhost:8080/execute/workflow/${id}`
            );

            startPolling();

        } catch(error) {

            console.error(error);
            setExecuting(false);
        }
    };

    const startPolling = () => {

        const intervalId = setInterval(
            async () => {

                const response =
                    await axios.get(
                        `http://localhost:8080/workflows/get-workflow-by-workflowId/${id}`
                    );

                setWorkflow(response.data);

                if (
                    response.data.status === "SUCCESS"
                    ||
                    response.data.status === "FAILED"
                ) {

                    clearInterval(intervalId);

                    setExecuting(false);
                }

            },
            2000
        );

    };

    const fetchExecutions =
        async () => {

        try {

            const response =
                await axios.get(
                    `http://localhost:8080/workflow-executions-history/workflow/${id}`
                );

            setExecutions(
                response.data
            );

        } catch(error) {

            console.error(error);
        }
    };

    if (loading) {

        return (
            <div className="p-8">
                Loading workflow...
            </div>
        );
    }

    if (!workflow) {

        return (
            <div className="p-8">
                Workflow not found
            </div>
        );
    }
    return (
    <div className="p-8">

        {/* Workflow Header */}
        <h1
            className="
                text-3xl
                font-bold
                mb-6
            "
        >
            {workflow?.name}
        </h1>

        {/* Workflow Info Card */}
        <div
            className="
                bg-white
                rounded-lg
                shadow-md
                p-6
                mb-6
            "
        >

            <p>
                Status:
                <strong>
                    {" "}
                    {workflow?.status}
                </strong>
            </p>

            <p>
                Created By:
                <strong>
                    {" "}
                    {workflow?.user?.username}
                </strong>
            </p>

            {/* Action Buttons */}
            <div className="flex gap-3 mt-4">

                <button
                    onClick={() =>
                        setShowCreateTaskModal(true)
                    }
                    className="
                        bg-blue-500
                        text-white
                        px-4
                        py-2
                        rounded-lg
                    "
                >
                    Add Task
                </button>

                <button
                    onClick={() =>
                        setShowAddDependencyModal(true)
                    }
                    className="
                        bg-purple-600
                        text-white
                        px-4
                        py-2
                        rounded-lg
                    "
                >
                    Add Dependency
                </button>
                
                    {/* add tasks */}
                {
                    hasTasks && (
                        <button
                            onClick={executeWorkflow}
                            disabled={executing}
                            className="
                                bg-green-600
                                text-white
                                px-4
                                py-2
                                rounded-lg
                            "
                        >
                            {
                                executing
                                    ? "Executing..."
                                    : "Execute Workflow"
                            }
                        </button>
                    )
                }

                {/* add dependency tasks */}
                {
                    showAddDependencyModal && (

                        <AddDependencyModal

                            tasks={workflow.tasks}

                            onClose={() =>
                                setShowAddDependencyModal(
                                    false
                                )
                            }

                            onDependencyAdded={
                                handleDependencyAdded
                            }

                        />

                    )
                }

            </div>

        </div>

        {/* Create Task Modal */}
        {
            showCreateTaskModal && (

                <CreateTaskModal

                    workflowId={id}

                    onClose={() =>
                        setShowCreateTaskModal(false)
                    }

                    onTaskCreated={
                        handleTaskCreated
                    }

                />

            )
        }

        {/* Empty State */}
        {
            !hasTasks && (

                <div
                    className="
                        bg-gray-50
                        border
                        rounded-lg
                        p-8
                        text-center
                    "
                >

                    <h2
                        className="
                            text-xl
                            font-semibold
                            mb-2
                        "
                    >
                        No Tasks Added Yet
                    </h2>

                    <p className="text-gray-500">
                        Create your first task
                        to start building the workflow.
                    </p>

                </div>

            )
        }

        {/* Tasks Section */}
        {
            hasTasks && (

                <div className="mt-8">

                    <h2
                        className="
                            text-2xl
                            font-bold
                            mb-4
                        "
                    >
                        Tasks
                    </h2>

                    <TaskList
                        tasks={workflow.tasks} onDelete={handleDeleteTask}
                    />

                </div>

            )
        }

        {/* DAG Graph */}
        {
            hasTasks && (

                <div className="mt-8">

                    <h2
                        className="
                            text-2xl
                            font-bold
                            mb-4
                        "
                    >
                        Workflow Graph
                    </h2>

                    <WorkflowGraph
                        tasks={workflow.tasks}
                    />

                </div>

            )
        }

        {/* Execution History */}
        {
            hasTasks && (

                <div className="mt-8">

                    <h2
                        className="
                            text-2xl
                            font-bold
                            mb-4
                        "
                    >
                        Execution History
                    </h2>

                    <ExecutionHistory
                        executions={executions}
                    />

                </div>

            )
        }

    </div>
);
}

export default WorkflowDetails;