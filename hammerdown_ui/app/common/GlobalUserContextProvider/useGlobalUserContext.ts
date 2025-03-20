import { createContext, useContext } from "react";
import type { GlobalUserType } from "~/model";

type GlobalUserContextType = {
    globalUser : GlobalUserType | null ;
    setGlobalUser : (data : GlobalUserType | null) => void ;
}

export const GlobalUserContext = createContext<GlobalUserContextType | undefined>(
  undefined
);

export const useGlobalUserContext = () => {
  const globalUserContext = useContext(GlobalUserContext);

  if (globalUserContext === undefined) {
    throw new Error("useGlobalUserContext must be used inside a Provider");
  }

  return globalUserContext;
};
