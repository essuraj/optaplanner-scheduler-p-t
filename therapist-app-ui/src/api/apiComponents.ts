/**
 * Generated by @openapi-codegen
 *
 * @version v0
 */
import * as reactQuery from "@tanstack/react-query";
import { useApiContext, ApiContext } from "./apiContext";
import type * as Fetcher from "./apiFetcher";
import { apiFetch } from "./apiFetcher";
import type * as Schemas from "./apiSchemas";

export type RunError = Fetcher.ErrorWrapper<undefined>;

export type RunVariables = ApiContext["fetcherOptions"];

export const fetchRun = (variables: RunVariables, signal?: AbortSignal) =>
  apiFetch<Schemas.SolveResponse, RunError, undefined, {}, {}, {}>({
    url: "/run",
    method: "get",
    ...variables,
    signal,
  });

export const useRun = <TData = Schemas.SolveResponse,>(
  variables: RunVariables,
  options?: Omit<
    reactQuery.UseQueryOptions<Schemas.SolveResponse, RunError, TData>,
    "queryKey" | "queryFn" | "initialData"
  >,
) => {
  const { fetcherOptions, queryOptions, queryKeyFn } = useApiContext(options);
  return reactQuery.useQuery<Schemas.SolveResponse, RunError, TData>({
    queryKey: queryKeyFn({ path: "/run", operationId: "run", variables }),
    queryFn: ({ signal }) =>
      fetchRun({ ...fetcherOptions, ...variables }, signal),
    ...options,
    ...queryOptions,
  });
};

export type DemoDataError = Fetcher.ErrorWrapper<undefined>;

export type DemoDataVariables = ApiContext["fetcherOptions"];

export const fetchDemoData = (
  variables: DemoDataVariables,
  signal?: AbortSignal,
) =>
  apiFetch<Schemas.Schedule, DemoDataError, undefined, {}, {}, {}>({
    url: "/demo-data",
    method: "get",
    ...variables,
    signal,
  });

export const useDemoData = <TData = Schemas.Schedule,>(
  variables: DemoDataVariables,
  options?: Omit<
    reactQuery.UseQueryOptions<Schemas.Schedule, DemoDataError, TData>,
    "queryKey" | "queryFn" | "initialData"
  >,
) => {
  const { fetcherOptions, queryOptions, queryKeyFn } = useApiContext(options);
  return reactQuery.useQuery<Schemas.Schedule, DemoDataError, TData>({
    queryKey: queryKeyFn({
      path: "/demo-data",
      operationId: "demoData",
      variables,
    }),
    queryFn: ({ signal }) =>
      fetchDemoData({ ...fetcherOptions, ...variables }, signal),
    ...options,
    ...queryOptions,
  });
};

export type QueryOperation =
  | {
      path: "/run";
      operationId: "run";
      variables: RunVariables;
    }
  | {
      path: "/demo-data";
      operationId: "demoData";
      variables: DemoDataVariables;
    };
