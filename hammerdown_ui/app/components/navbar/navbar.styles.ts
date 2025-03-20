import styled from "styled-components";

export const StyledButton = styled.button`
  padding: 5px 5px;
  background: green;
  cursor: pointer;
  margin: 5px;
`;

export const BigStyleButton = styled.button<{ $size: number }>`
  padding: 5px 5px;
  background: red;
  cursor: pointer;
  margin: 5px;
  font-size: ${(props) => String(props.$size)}px;
`;
