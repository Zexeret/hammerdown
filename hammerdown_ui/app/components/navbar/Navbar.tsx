import React, { useEffect } from "react";
import { useNavigate } from "react-router";
import { useGlobalUserContext } from "~/common/GlobalUserContextProvider";
import { BigStyleButton, StyledButton } from "./navbar.styles";

export const Navbar = () => {

  const {globalUser} = useGlobalUserContext() ;
  const navigate = useNavigate() ;

  return (
    <>
      <StyledButton onClick={() => navigate("/")}>About</StyledButton>
      <BigStyleButton $size={20} onClick={() => navigate("/")} >Home</BigStyleButton>
      <button  onClick={() => navigate("/post/2")}>Post</button>
      {globalUser && <button onClick={() => navigate("/profile")}>Profile</button>}
      <br />
    </>
  );
};
