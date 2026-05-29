import { useState } from "react";
import WorkflowCard from "../components/WorkflowCard";

function WorkflowList({workflows, onDelete}){
    return(
        <div className="space-y-4">
            {workflows.map(workflow => (
                <WorkflowCard key={workflow.id} workflow = {workflow} handleDelete={onDelete}/>
            ))}
        </div>
    )
}

export default WorkflowList;