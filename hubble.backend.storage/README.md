# Diseño de colecciones en MongoDB

## Aplicaciones

La colección se llama __applicationStorage__. Registra las aplicaciones administradas en Hubble.

    applications {
        name: "string",
        description: "string",
        active: "boolean",
        kpis: {
            enabled: "string[]",
            tasks: {
                thresholds: {
                    normal: "integer",
                    warning: "integer",
                    critical: "integer"
                },
                providers: {
                    ppm: {
                        enabled: "boolean",
                        applicationName: "string",
                        enabledInTaskRunner: "boolean"
                    }
                }   
            },
            defects: {
                thresholds: {
                    normal: "integer",
                    warning: "integer",
                    critical: "integer"
                },
                providers: {
                    alm: {
                        enabled: "boolean",
                        applicationName: "string",
                        enabledInTaskRunner: "boolean"
                    },
                    jira: {
                        enabled: "boolean",
                        applicationName: "string",
                        enabledInTaskRunner: "boolean"
                    }
                }
            },
            defects: {
                thresholds: {
                    normal: "double",
                    warning: "double",
                    critical: "double"
                },
                providers: {
                    bsm: {
                        enabled: "boolean",
                        enabledInTaskRunner: "boolean"
                    }
                }
            },
            performance: {
                thresholds: {
                    normal: "double",
                    warning: "double",
                    critical: "double"
                },
                providers: {
                    appPulse: {
                        enabled: "boolean",
                        enabledInTaskRunner: "boolean"
                    }
                }
            },
            events: {
                thresholds: {
                    normal: "integer",
                    warning: "integer",
                    critical: "integer"
                },
                providers: {
                    siteScope: {
                        enabled: "boolean",
                        enabledInTaskRunner: "boolean"
                    }
                }
            }
        }
    }

### Ejemplo


    {
        _id: 1
        name: "Home Banking",
        description: "Home Banking es una aplicación que permite....",
        active: true,
        kpis: {
            enabled: ["tasks", "defects", "availability", "performance", "events"],
            tasks: {
                thresholds: {
                    normal: 1,
                    warning: 2,
                    critical: 5
                },
                providers: {
                    ppm: {
                        enabled: true,
                        applicationName: "Benchmark Home Banking",
                        enabledInTaskRunner: true
                    }
                }   
            },
            defects: {
                thresholds: {
                    normal: 1,
                    warning: 2,
                    critical: 5
                },
                providers: {
                    alm: {
                        enabled: true,
                        applicationName: "Benchmark Home Banking",
                        enabledInTaskRunner: true
                    },
                    jira: {
                        enabled: true,
                        applicationName: "Benchmark Home Banking",
                        enabledInTaskRunner: true
                    },
                }
            },
            availability: {
                thresholds: {
                    normal: 99.9999,
                    warning: 75,
                    critical: 50
                },
                providers: {
                    bsm: {
                        enabled: true,
                        enabledInTaskRunner: true
                    }
                }
            },
            performance: {
                thresholds: {
                    normal: 1.0,
                    warning: 10.0,
                    critical: 100.0
                },
                providers: {
                    appPulse: {
                        enabled: true,
                        enabledInTaskRunner: true
                    }
                }
            },
            events: {
                thresholds: {
                    normal: 1,
                    warning: 2,
                    critical: 5
                },
                providers: {
                    siteScope: {
                        enabled: true,
                        enabledInTaskRunner: true
                    }
                }
            }
        }
    }

## Proveedores

La colección de proveedores tiene pocos atributos comunes.

    {
        name: "string",
        enabled: "boolean",
        environment: "document",
        configuration: "document",
        taskRunner: {
            enabled: "boolean",
            cronExpression: "string"
        }
    }

Los atributos puntuales de los atributos _environment_ y _configuration_ dependerán de cada proveedor.

### ALM

    {
        name: "ALM",
        enabled: true,
        environment: {
            host: "ppm.tsoft.com.ar",
            port: 80,
            username: "martin.straus",
            password: "",
            domain: "TSAR_SOFTWAREFACTORY",
            project: "HUBBLE"
        },
        configuration: {
            businessApplicationFieldName: "project",
            status: {
                status: "status",
                openValues: ["Nuevo", "Abierto", "Reabierto"]
            },
            transactionFieldName: "project"
            provider: {
                origin: "Alm",
                name: "Alm Tsoft",
            }
        },
        taskRunner: {
            enabled: true,
            cronExpression: "0 0 0 * * *"
        }
    }

## AppPulse

    {
        name: "AppPulse",
        enabled: true,
        environment: {
            url: "https://apppulse-active.saas.hpe.com/openapi/rest/v1/949143007/",
            client: "949143007#C1",
            secret: "d3e5ad40-4eca-48d0-9db0-a410f76b45e7"
        },
        configuration: {},
        taskRunner: {
            enabled: true,
            cronExpression: "0 0 0 * * *"
        }
    }


## BSM

    {
        name: "BSM",
        enabled: true,
        environment: {
            soap: {
                endpoint: "https://apppulse-active.saas.hpe.com/openapi/rest/v1/949143007/",
                action: "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI",
            },
            username: "admin",
            password: "admin"
        },
        configuration: {},
        taskRunner: {
            enabled: true,
            cronExpression: "0 0 0 * * *"
        }
    }


## Jira

    {
        name: "Jira",
        enabled: true,
        environment: {
            host: "10.10.20.175",
            port: 8888,
            username: "andrevigneaux",
            password: ""
        },
        configuration: {
            projectKey: "HUB",
            businessApplicationFieldName: "project"
        },
        taskRunner: {
            enabled: true,
            cronExpression: "0 0 0 * * *"
        }
    }

## Jira

    {
        name: "PPM",
        enabled: true,
        environment: {
            host: "ppm.tsoft.com.ar",
            port: 80,
            username: "admin",
            password: "ppm931"
        },
        configuration: {
            businessApplicationFieldName: "project"
            transactionFieldName: "not_defined",
            provider: {
                origin: "Ppm",
                name: "Ppm Tsoft",
            },
            requestTypeIds: [30219]
        },
        taskRunner: {
            enabled: true,
            cronExpression: "0 0 0 * * *"
        }
    }


## SiteScope

    {
        name: "SiteScope",
        enabled: true,
        environment: {},
        configuration: {},
        taskRunner: {
            enabled: true,
            cronExpression: "0 0 0 * * *"
        }
    }
