"use client";

import * as React from "react";

import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { cn } from "@/lib/utils";
import { useState } from "react";
import { useUserStore } from "@/store/userStore";
import { useNavigate } from "react-router-dom";

interface UserAuthFormProps extends React.HTMLAttributes<HTMLDivElement> {}

export function UserAuthForm({ className, ...props }: UserAuthFormProps) {
  const [isLoading, setIsLoading] = React.useState<boolean>(false);
  const [id, setId] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const { login } = useUserStore();
  const navigate = useNavigate();

  async function onSubmit(event: React.SyntheticEvent) {
    event.preventDefault();
    try {
      setIsLoading(true);
      const IsLoggedIn = await login(id, password);
      if (IsLoggedIn) {
        navigate("/");
      } else {
        alert("잘못된 ID/PW입니다.");
      }
    } catch (err) {
      console.error("Login Error");
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className={cn("grid gap-6", className)} {...props}>
      <form onSubmit={onSubmit}>
        <div className="grid gap-2">
          <div className="grid gap-3">
            <Label className="text-muted-foreground" htmlFor="id">
              포털 ID
            </Label>
            <Input
              id="id"
              placeholder="some@uos.ac.kr"
              type="id"
              autoCapitalize="none"
              autoComplete="id"
              autoCorrect="off"
              disabled={isLoading}
              onChange={(e) => setId(e.target.value)}
            />
            <Label className="text-muted-foreground" htmlFor="pw">
              포털 비밀번호
            </Label>
            <Input
              id="pw"
              placeholder=""
              type="password"
              autoCapitalize="none"
              autoCorrect="off"
              disabled={isLoading}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <Button className="bg-indigo-600" disabled={isLoading}>
            {isLoading ? (
              <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
            ) : (
              <span>로그인</span>
            )}
          </Button>
        </div>
      </form>
    </div>
  );
}
