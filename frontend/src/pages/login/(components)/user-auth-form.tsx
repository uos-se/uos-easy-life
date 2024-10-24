"use client"

import * as React from "react"

import { Icons } from "@/components/icons"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useSessionContext } from "@/context/useSessionContext"
import { cn } from "@/lib/utils"
import { useState } from "react"

interface UserAuthFormProps extends React.HTMLAttributes<HTMLDivElement> {}

export function UserAuthForm({ className, ...props }: UserAuthFormProps) {
  const [isLoading, setIsLoading] = React.useState<boolean>(false)
  const [id, setId] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const { login } = useSessionContext()

  async function onSubmit(event: React.SyntheticEvent) {
    event.preventDefault()
    setIsLoading(true)

    login(id, password)
    setIsLoading(false)
  }

  return (
    <div className={cn("grid gap-6", className)} {...props}>
      <form onSubmit={onSubmit}>
        <div className="grid gap-2">
          <div className="grid gap-3">
            <Label className="text-muted-foreground" htmlFor="id">
              Your Portal ID
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
              Your Portal Password
            </Label>
            <Input
              id="pw"
              placeholder="password"
              type="password"
              autoCapitalize="none"
              autoCorrect="off"
              disabled={isLoading}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <Button disabled={isLoading}>
            {isLoading && (
              <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
            )}
            로그인
          </Button>
        </div>
      </form>
    </div>
  )
}

