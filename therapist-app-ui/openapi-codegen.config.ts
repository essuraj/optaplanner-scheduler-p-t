import { defineConfig } from "@openapi-codegen/cli";
import {
  generateSchemaTypes,
  generateReactQueryComponents,
} from "@openapi-codegen/typescript";
export default defineConfig({
  api: {
    from: {
      source: "url",
      url: "http://localhost:8080/v3/api-docs",
    },
    outputDir: "./src/api",
    to: async (context) => {
      const filenamePrefix = "api";
      const { schemasFiles } = await generateSchemaTypes(context, {
        filenamePrefix,
      });
      await generateReactQueryComponents(context, {
        filenamePrefix,
        schemasFiles,
      });
    },
  },
});
