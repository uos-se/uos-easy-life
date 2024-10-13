import { useContext } from "react";
import { SessionContext } from "./SessionContext";

export const useSessionContext = () => useContext(SessionContext);
