import { useParams } from "react-router";
import type { Route } from "./+types/post";


export default function Post({}: Route.ComponentProps) {
    let params = useParams() ;
    const postId = params.postId; 
    return <>Shyam in Post {postId}</>;
}