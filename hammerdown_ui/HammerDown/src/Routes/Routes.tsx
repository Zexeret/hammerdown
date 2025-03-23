import { createBrowserRouter } from "react-router";
import App from "../App";
import Login from "../Pages/Login/Login";
import SignUp from "../Pages/SignUp/SignUp";
import Home from "../Pages/Home/Home";
import UserProfile from "../Pages/UserProfile/UserProfile";
import MyProducts from "../Pages/MyProducts/MyProducts";

export const router = createBrowserRouter([
    {
        path: '/',
        element:<App/>,
        children:[
                {path:"" , element:<Home />},
                {path:"login",element:<Login />},
                {path:"signUp",element:<SignUp />},
                {path:"profile",element:<UserProfile/>},
                {path:"explore",element:<Home/>},
                {path:"myProducts",element:<MyProducts/>},
                {path:"myProducts/:id",element:<MyProducts/>},
        ]
    },
    
])