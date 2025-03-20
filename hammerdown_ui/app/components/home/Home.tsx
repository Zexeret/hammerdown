import React, { useEffect } from "react";
import { useNavigate } from "react-router";
import { useGlobalUserContext } from "~/common/GlobalUserContextProvider";

export const HomePage = () => {

  const {globalUser} = useGlobalUserContext() ;

  return (
    <>
      <div>Home Page!!</div>
      <div>{globalUser?.username}</div>
    </>
  );
};
