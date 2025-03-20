export const ApiCategory = {
    API_PUBLIC_HELLO: "API_PUBLIC_HELLO",
} as const;

export type ApiCategoryType = keyof typeof ApiCategory;

type FetchInstanceDetails = {
    url : string ;
    method : 'get' | 'post' | 'put' | 'delete' ;
}

export const apiCategoryToDetailsMap : Record<ApiCategoryType, FetchInstanceDetails> = {
    API_PUBLIC_HELLO : {
        url : "/public/hello",
        method : 'get'
    }
}