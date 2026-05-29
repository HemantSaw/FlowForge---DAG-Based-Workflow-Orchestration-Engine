import { useState } from "react";
import axios from "axios";

function CreateTaskModal({workflowId, onClose, onTaskCreated}) {
    const [name, setName] = useState("");
    const [taskType, setTaskType] = useState("FETCH_DATA");

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response =
                await axios.post(

                    `http://localhost:8080/tasks/workflow/${workflowId}`,

                    {
                        name,
                        status:"PENDING",
                        taskType,
                    }

                );

            onTaskCreated(
                response.data
            );

            onClose();

        } catch(error) {

            console.error(error);
        }
    };

    return (

        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">

            <div className="bg-white p-6 rounded-lg w-96 z-50">

                <h2 className="text-xl font-bold mb-4">
                    Add Task
                </h2>

                <form onSubmit={handleSubmit}>

                    <input type="text"
                        placeholder="Task Name"
                        value={name}
                        onChange={(e) =>
                            setName(
                                e.target.value
                            )
                        }
                        className="border p-2 w-full rounded mb-4"/>

                    <select
                        value={taskType}
                        onChange={(e) =>
                            setTaskType(
                                e.target.value
                            )
                        }
                        className="border p-2 w-full rounded mb-4" >   

                        <option>
                        FETCH_DATA
                        </option>

                        <option>
                        VALIDATE_DATA
                        </option>

                        <option>
                        GENERATE_REPORT
                        </option>

                        <option>
                        STORE_ANALYTICS
                        </option>

                        <option>
                        SEND_EMAIL
                        </option>

                    </select>
                    <div
                        className="
                            flex
                            gap-2
                        "
                    >
                        <button
                            type="submit"
                            className="bg-blue-500 text-white px-4 py-2 rounded">
                            Create
                        </button>
                        <button
                                type="button"
                                onClick={onClose}
                                className="
                                    bg-gray-300
                                    px-4
                                    py-2
                                    rounded
                                "
                            >
                                Cancel
                        </button>
                    </div>


                </form>

            </div>

        </div>

    );
}

export default CreateTaskModal;