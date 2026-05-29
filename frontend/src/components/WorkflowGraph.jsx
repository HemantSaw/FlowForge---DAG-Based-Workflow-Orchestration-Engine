import {ReactFlow, MarkerType} from "@xyflow/react";
import "@xyflow/react/dist/style.css";

function WorkflowGraph({tasks}) {

    // making graph level-by-level
    const inDegree = {};
    const queue = [];
    const buildLevels = (tasks) => {

        tasks.forEach(task => {
            inDegree[task.id] =
                task.dependsOn.length;
        });

        //finding root
        tasks.forEach(task => {
            if(inDegree[task.id] === 0) {
                queue.push({ task :task, level: 0 });
            }
        });

        // making adjecency list.
        const childrenMap = {};
        tasks.forEach(task =>{
            childrenMap[task.id] = [];
        })
        tasks.forEach(task =>{
            task.dependsOn.forEach(dep =>{
                childrenMap[dep.id].push(task);
            })
        })

        // using Kahn's ALgorithm (Topological sorting)
        const levels = {};
        while(queue.length > 0){
            const current = queue.shift();
            const task = current.task;
            const level = current.level;

            levels[task.id] = level;
            childrenMap[task.id].forEach(child =>{
                inDegree[child.id]--;
                if(inDegree[child.id] === 0){
                    queue.push({task: child, level :level+1});
                }
            });
        }
        return levels;
    };

    const levels = buildLevels(tasks);

    const tasksByLevel = {};
    tasks.forEach(task =>{
        const level = levels[task.id];
        if(!tasksByLevel[level]){
            tasksByLevel[level] = [];
        }
        tasksByLevel[level].push(task);
    })

    // making nodes
    const nodes = [];
    Object.entries(tasksByLevel)
    .forEach(([level, levelTasks]) => {

        levelTasks.forEach(
            (task, index) => {

                nodes.push({

                    id:
                        String(task.id),

                    data: {
                        label:
                            task.name
                    },

                    position: {

                        x:
                            index * 250,

                        y:
                            level * 150

                    }

                });

            });

    });

    // making edges.
    const edges = [];
    tasks.forEach(task => {

        task.dependsOn.forEach(dep => {

            edges.push({

                id:
                    `${dep.id}-${task.id}`,

                source:
                    String(dep.id),

                target:
                    String(task.id),
                markerEnd: {
                    type: MarkerType.ArrowClosed, // or MarkerType.Arrow
                    width: 20,
                    height: 20,
                    color: '#0b0a0aff',
                },  

            });

        });
    });



    return (
        <div
            style={{
                width: "100%",
                height: "500px"
            }}
        >
            <ReactFlow
                nodes={nodes}
                edges={edges}
            />
        </div>
    );
}

export default WorkflowGraph;