import { Outlet } from "react-router";
import type { Route } from "./+types/signin";
import { SignIn } from "~/components";

export default function SignInRoute({} : Route.ComponentProps) {
    return <SignIn />;
}
  