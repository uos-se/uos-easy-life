import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { UserAuthForm } from "./components/UserAuthForm";
import { useSessionStore } from "@/store/sessionStore";

export function Login() {
  const { session } = useSessionStore();
  const nav = useNavigate();

  // Check if the session is valid. If valid, redirect to the main page.
  useEffect(() => {
    if (session) nav("/");
  }, [session, nav]);

  return (
    <>
      <div className="relative grid h-screen flex-col items-center justify-center md:grid lg:max-w-none lg:grid-cols-2 lg:px-0">
        <div className="relative hidden h-full flex-col bg-muted p-10 text-white dark:border-r lg:flex">
          <div className="absolute inset-0 bg-indigo-900" />
          <div className="relative z-20 flex items-center text-lg font-medium">
            졸업해줘
          </div>
          <div className="relative z-20 mt-auto">
            <blockquote className="space-y-2">
              <p className="text-lg">
                &ldquo; 졸업은 끝이 아니라 새로운 시작이다... 하지만 그 전에
                먼저 졸업을 해야 한다. &rdquo;
              </p>
              <footer className="text-sm">Graduos</footer>
            </blockquote>
          </div>
        </div>
        <div className="p-8">
          <div className="mx-auto flex w-full flex-col justify-center space-y-6 sm:w-[350px]">
            <div className="flex flex-col space-y-2 text-center">
              <h1 className="text-2xl font-semibold tracking-tight">로그인</h1>
              <p className="text-sm text-muted-foreground">
                서울시립대 포털 아이디 비밀번호를 적어주세요
              </p>
            </div>
            <UserAuthForm />
            <p className="px-8 text-center text-sm text-muted-foreground">
              By clicking continue, you agree to our{" "}
              <a
                href="/terms"
                className="underline underline-offset-4 hover:text-primary"
              >
                Terms of Service
              </a>{" "}
              and{" "}
              <a
                href="/privacy"
                className="underline underline-offset-4 hover:text-primary"
              >
                Privacy Policy
              </a>
              .
            </p>
          </div>
        </div>
      </div>
    </>
  );
}
