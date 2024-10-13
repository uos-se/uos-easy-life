import { createContext } from "react";

export interface Session {
  key: string;
  id: string;
  password: string;
}

export interface SessionContextType {
  session: Session | null;
  login: (id: string, password: string) => void;
  logout: () => void;
}

export const SessionContext = createContext<SessionContextType>(
  {} as unknown as SessionContextType
);
