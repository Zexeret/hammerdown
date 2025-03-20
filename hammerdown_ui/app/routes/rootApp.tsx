import { RootApp } from "~/components";
import type { Route } from "./+types/rootApp";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Bidding App" },
    { name: "description", content: "Welcome to Bidding App!" },
  ];
}


export default function RootAppRoute() {
  return <RootApp  />;
}
