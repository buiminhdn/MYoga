import React, { createContext, useContext, useState, useEffect } from "react";

interface UserContextType {
  userId: string | null;
  setUserId: React.Dispatch<React.SetStateAction<string | null>>;
}

const UserContext = createContext<UserContextType | null>(null);

import { ReactNode } from "react";

export const UserProvider = ({ children }: { children: ReactNode }) => {
  const [userId, setUserId] = useState<string | null>(null);

  useEffect(() => {
    // Set a test user ID on app startup
    setUserId("testUserId123");
  }, []);

  return (
    <UserContext.Provider value={{ userId, setUserId }}>
      {children}
    </UserContext.Provider>
  );
};

export const useUser = () => useContext(UserContext);
