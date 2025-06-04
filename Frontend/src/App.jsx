import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Inicio from './assets/Components/Inicio.jsx'
import Calendario from './assets/Components/Calendario.jsx'

function App() {
  

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Inicio/>} />
      </Routes>
    </Router>
  )
}

export default App
