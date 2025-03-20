import {
  type RouteConfig,
  index,
  layout,
  route,
} from "@react-router/dev/routes";

// export default [index("routes/home.tsx"),
//     route("post/:postId","routes/post.tsx"),
//     route("dashboard","routes/dashboard.tsx", [
//         route("finance","routes/finances.tsx"),
//         route("invoice","routes/invoice.tsx")
//     ]),
// ] satisfies RouteConfig;

export default [
  layout("routes/rootApp.tsx", [
    route("signin", "routes/signin.tsx"),
    route("signup", "routes/signup.tsx"),
    index("routes/home.tsx"),
    route("profile", "routes/profile.tsx"),
    route("post/:postId", "routes/post.tsx"),
  ]),
] satisfies RouteConfig;
