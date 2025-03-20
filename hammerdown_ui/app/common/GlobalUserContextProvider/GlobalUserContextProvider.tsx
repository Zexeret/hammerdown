import { useEffect, useState, type ReactNode } from "react";
import { GlobalUserContext } from "./useGlobalUserContext";
import type { GlobalUserType } from "~/model";
import { useNavigate } from "react-router";

type GlobalUserContextProviderType = {
  children: ReactNode;
};

const tempUser : GlobalUserType = {
    username: "user1",
    email : "user@email.com"
} 

export const GlobalUserContextProvider = (
  props: GlobalUserContextProviderType
) => {
  const [globalUser, setGlobalUser] = useState<GlobalUserType | null>(null);
  const navigate = useNavigate() ;
  console.log("INside priovider") ;

  // simulate a fetching of api call;
  useEffect(() => {
  console.log("INside priovider useEffect") ;

    setTimeout(() => {
        // Creating 1/5 chance of failure, indicating user is not authenticated
        const err = Math.random()*5;
        if(err < 1){

            navigate("/signin") ;
        }else{
            console.log("Setting user to ", tempUser) ;
            setGlobalUser(tempUser) ;
        }

    }, 2000);
  },[])

  return (
    <GlobalUserContext.Provider value={{ globalUser, setGlobalUser }}>
      {props.children}
    </GlobalUserContext.Provider>
  );
};
