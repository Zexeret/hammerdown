import { Outlet } from "react-router";
import { GlobalUserContextProvider } from "~/common/GlobalUserContextProvider";
import { Navbar } from "./navbar";

export const RootApp = () => {
  return (
      <GlobalUserContextProvider>
            <Navbar />
            <Outlet />
      </GlobalUserContextProvider>
  );
};
