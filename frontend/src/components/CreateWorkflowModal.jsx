import { useState } from "react";
import axios from "axios";

function CreateWorkflowModal({onClose,onWorkflowCreated}) {

    const [name, setName]
        = useState("");

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response =
                await axios.post(
                    "http://localhost:8080/workflows/user/1",
                    {
                        name: name,
                        status : "PENDING"
                    }
                );

            onWorkflowCreated(
                response.data
            );

            onClose();

        } catch(error) {

            console.error(error);

        }

    };

    return (

        <div
            className="
                fixed
                inset-0
                bg-black/50
                flex
                items-center
                justify-center
            "
        >

            <div
                className="
                    bg-white
                    p-6
                    rounded-lg
                    w-96
                "
            >

                <h2
                    className="
                        text-xl
                        font-bold
                        mb-4
                    "
                >
                    Create Workflow
                </h2>

                <form
                    onSubmit={
                        handleSubmit
                    }
                >

                    <input
                        type="text"
                        placeholder="Workflow Name"
                        value={name}
                        onChange={(e) =>
                            setName(
                                e.target.value
                            )
                        }
                        className="
                            border
                            w-full
                            p-2
                            rounded
                            mb-4
                        "
                    />

                    <div
                        className="
                            flex
                            gap-2
                        "
                    >

                        <button
                            type="submit"
                            className="
                                bg-blue-500
                                text-white
                                px-4
                                py-2
                                rounded
                            "
                        >
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

export default CreateWorkflowModal;