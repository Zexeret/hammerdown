import { Outlet } from "react-router"
import Navbar from "./Components/Navbar/Navbar"

function App() {

  const personName = {
    fname: 'Vegeta',
    lname: 'Prince'
  }
  return (
    <>
      <Navbar />
      <Outlet />
    </>
  )
}

export default App
