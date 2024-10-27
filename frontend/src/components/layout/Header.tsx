import { useSessionContext } from "@/context/useSessionContext";

export function Header() {
  const { logout } = useSessionContext();

  return (
    <header className="bg-white p-6 shadow-md">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        <div className="text-2xl font-bold text-indigo-600">졸업해줘</div>
        <button 
          onClick={() => logout()}
          className="text-gray-600 hover:text-indigo-600 transition-colors duration-200"
        >
          Logout
        </button>
      </div>
    </header>
  );
}
