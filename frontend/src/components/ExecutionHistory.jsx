import ExecutionCard from "./ExecutionCard";

function ExecutionHistory({executions}) {

    return (

        <div className="space-y-4">

            {executions.map(execution => (

                <ExecutionCard
                    key={execution.id}
                    execution={execution}
                />

            ))}

        </div>

    );
}

export default ExecutionHistory;