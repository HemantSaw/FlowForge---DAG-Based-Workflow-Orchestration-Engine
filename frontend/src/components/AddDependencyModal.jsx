import { useState } from "react";
import axios from "axios";

function AddDependencyModal({tasks,onClose,onDependencyAdded}) {
    const [taskId, setTaskId] = useState("");

    const [dependencyTaskId,setDependencyTaskId] = useState("");

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await axios.post(

                `http://localhost:8080/tasks/${taskId}/depends-on/${dependencyTaskId}`

            );

            onDependencyAdded();

            onClose();

        } catch(error) {
            alert(
                error.response.data || "Something went wrong."
            );
            console.error(error);
        }
    };

    return(
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">

            <div className="bg-white p-6 rounded-lg w-96 z-50">

                <h2 className="text-xl font-bold mb-4">
                    Add Dependencies
                </h2>

                <form onSubmit={handleSubmit}>
                    <select
                        value={taskId}
                        onChange={(e) =>
                            setTaskId(
                                e.target.value
                            )
                        }
                        className="
                            border
                            p-2
                            w-full
                            rounded
                            mb-4
                        "
                    >

                        <option value="">
                            Select Task
                        </option>

                        {
                            tasks.map(task => (

                                <option
                                    key={task.id}
                                    value={task.id}
                                >
                                    {task.name}
                                </option>

                            ))
                        }

                    </select>

                    <select
                        value={dependencyTaskId}
                        onChange={(e) =>
                            setDependencyTaskId(
                                e.target.value
                            )
                        }
                        className="
                            border
                            p-2
                            w-full
                            rounded
                            mb-4
                        "
                    >

                        <option value="">
                            Depends On
                        </option>

                        {
                            tasks.filter(task =>task.id != taskId).map(task => (

                                <option
                                    key={task.id}
                                    value={task.id}
                                >
                                    {task.name}
                                </option>

                            ))
                        }

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
                            Add
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
    )
}

export default AddDependencyModal;