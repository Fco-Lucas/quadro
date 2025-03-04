CREATE TABLE "users" (
    "id" UUID NOT NULL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "cpf" INTEGER NOT NULL,
    "password" VARCHAR(255) NOT NULL
);