import axios from "axios";
import { useEffect, useState } from "react";
import WorkflowList from "../components/WorkflowList";
import CreateWorkflowModal from "../components/CreateWorkflowModal";

function Dashboard() {

    const [workflows, setWorkflows] = useState([]);
    const [showCreateWorkflowModal, setShowCreateWorkflowModal] = useState(false);

    useEffect(()=>{
        fetchWorkflow();
    },[]);

    const fetchWorkflow = async()=>{
        try{
            const response = await axios.get("http://localhost:8080/workflows");
            console.log(response);
            setWorkflows(response.data);
        } catch(error){
            console.error(error);
        }
    }
    const handleWorkflowCreated =
        (newWorkflow) => {

        setWorkflows([
            newWorkflow,
            ...workflows
        ]);

    };
    const handleDeleteWorkflow = async (workflowId) => {

        try {

            await axios.delete(
                `http://localhost:8080/workflows/delete-workflow-by-workflowId/${workflowId}`
            );

            setWorkflows(
                workflows.filter(
                    workflow =>
                        workflow.id !== workflowId
                )
            );

        } catch(error) {

            console.error(error);
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 p-8">

            <h1 className="text-4xl font-bold mb-8">
                Workflow Orchestration Engine
            </h1>

            <button
                onClick={() =>
                    setShowCreateWorkflowModal(true)
                }
                className="
                    bg-blue-500
                    text-white
                    px-4
                    py-2
                    rounded-lg
                    mb-6
                "
            >
                Create Workflow
            </button>
            {
                showCreateWorkflowModal && (

                    <CreateWorkflowModal
                        onClose={() =>
                            setShowCreateWorkflowModal(
                                false
                            )
                        }
                        onWorkflowCreated={
                            handleWorkflowCreated
                        }
                    />

                )
            }

            <WorkflowList workflows={workflows} onDelete={handleDeleteWorkflow}/>

        </div>

    );
}

export default Dashboard;