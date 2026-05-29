import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import TaskExecutionList from "./TaskExecutionList";

function TaskExecutionDetails(){
    const { executionId } = useParams();

    const [taskExecutions, setTaskExecutions] = useState([]);

    const [loading,setLoading] = useState(true);

    useEffect(() => {

        fetchTaskExecutions();

    }, [executionId]);

    const fetchTaskExecutions =
        async () => {

        try {

            const response =
                await axios.get(
                    `http://localhost:8080/task-executions-history/workflow-execution/${executionId}`
                );

            setTaskExecutions(
                response.data
            );

        } catch(error) {

            console.error(error);

        } finally {

            setLoading(false);

        }
    };

    if (loading) {

        return (
            <div>
                Loading...
            </div>
        );
    }
    return (

        <div className="p-8">

            <h1
                className="
                    text-3xl
                    font-bold
                    mb-6
                "
            >
                Execution Details #{executionId}
            </h1>

            <TaskExecutionList
                taskExecutions={
                    taskExecutions
                }
            />

        </div>

    );
}

export default TaskExecutionDetails