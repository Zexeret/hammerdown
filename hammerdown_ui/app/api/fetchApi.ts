import axios from "axios";
import { apiCategoryToDetailsMap, type ApiCategoryType } from "./apiConfig";

type FetchApiPropsType = {
  apiCategory: ApiCategoryType;
  payload ?: Object  ; // Maybe encode the paylaod and decode it in backend too ?
};

// Might Not be exactly same as one configured in backend. need to do it
export type ApiResponseType = {
  status: number;
  statusCode: string;
  data: Object | null;
  errorMessage: string;
};


export const fetchApi = async ({ apiCategory , payload }: FetchApiPropsType) => {
  const apiCategoryDetails = apiCategoryToDetailsMap[apiCategory];
  const baseUrl = `https:locahost:8080/api`;
  const url = `${baseUrl}/${apiCategoryDetails.url}`;

  const {status, statusText, data : response} = await axios<ApiResponseType>({
    method: apiCategoryDetails.method,
    url,
    withCredentials: true,
    headers: {
      "Content-Type": "application/json",
    },
  });

  if(response.status !== 200){
    console.log("Not successfull baby handleeeeeeee carefulyyy") ;
  }

  return response ;
};
