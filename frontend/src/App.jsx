import Dashboard from "./Pages/Dashboard";
import{Routes, Route} from 'react-router-dom'
import WorkflowDetails from "./pages/WorkflowDetails";
import TaskExecutionDetails from "./components/TaskExecutionDetails";

function App() {
  return (
    
    <Routes>
      <Route path="/workflows" element={<Dashboard />} />
      <Route path="/workflows/:id" element={<WorkflowDetails/>} />
      <Route path="/task-executions/:executionId" element={<TaskExecutionDetails />}/>
    </Routes>

  );
}

export default App;