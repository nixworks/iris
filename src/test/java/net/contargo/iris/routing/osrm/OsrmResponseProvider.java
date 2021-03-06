package net.contargo.iris.routing.osrm;

/**
 * @author  Sandra Thieme - thieme@synyx.de
 */
class OsrmResponseProvider {

    static String osrm4Response() {

        // /viaroute?loc=53.092984,8.691601&loc=53.082857,8.733542&z=0&geometry=false&instructions=true&alt=false
        return "{"
            + "  \"hint_data\": {"
            + "    \"locations\": ["
            + "      \"thqqAv____9a2QAAFQAAABoAAADMAAAAOAcAAA2JFAAAAAAAeCIqA5GfhAAJAAEB\","
            + "      \"buDiAf_____6BiAARgAAAFAAAAAzAAAARAEAAJCHeQAAAAAA5_opA2ZDhQABAAEB\""
            + "    ],"
            + "    \"checksum\": 34549117"
            + "  },"
            + "  \"route_name\": ["
            + "    \"A 281\\/;motorway\\/;yes\\/;DE\","
            + "    \"Senator-Apelt-Stra\\u00dfe\\/;tertiary\\/;no\\/;DE\""
            + "  ],"
            + "  \"via_indices\": ["
            + "    0,"
            + "    6"
            + "  ],"
            + "  \"found_alternative\": false,"
            + "  \"route_summary\": {"
            + "    \"end_point\": \"Merkurstra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "    \"start_point\": \"A 281\\/;motorway\\/;yes\\/;DE\","
            + "    \"total_time\": 972,"
            + "    \"total_distance\": 8249"
            + "  },"
            + "  \"via_points\": ["
            + "    ["
            + "      53.092983,"
            + "      8.691601"
            + "    ],"
            + "    ["
            + "      53.082855,"
            + "      8.733542"
            + "    ]"
            + "  ],"
            + "  \"route_instructions\": ["
            + "    ["
            + "      \"10\","
            + "      \"A 281\\/;motorway\\/;yes\\/;DE\","
            + "      4584,"
            + "      0,"
            + "      243,"
            + "      \"4584m\","
            + "      \"SE\","
            + "      145,"
            + "      1"
            + "    ],"
            + "    ["
            + "      \"2\","
            + "      \"\\/;motorway_link\\/;yes\\/;DE\","
            + "      707,"
            + "      1,"
            + "      42,"
            + "      \"706m\","
            + "      \"E\","
            + "      95,"
            + "      1"
            + "    ],"
            + "    ["
            + "      \"1\","
            + "      \"Senator-Apelt-Stra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "      2159,"
            + "      2,"
            + "      518,"
            + "      \"2159m\","
            + "      \"N\","
            + "      353,"
            + "      1"
            + "    ],"
            + "    ["
            + "      \"1\","
            + "      \"Rudolf-Diesel-Stra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "      367,"
            + "      3,"
            + "      88,"
            + "      \"366m\","
            + "      \"W\","
            + "      251,"
            + "      1"
            + "    ],"
            + "    ["
            + "      \"7\","
            + "      \"Merkurstra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "      432,"
            + "      4,"
            + "      99,"
            + "      \"432m\","
            + "      \"SE\","
            + "      117,"
            + "      1"
            + "    ],"
            + "    ["
            + "      \"15\","
            + "      \"\","
            + "      0,"
            + "      5,"
            + "      0,"
            + "      \"0m\","
            + "      \"N\","
            + "      0"
            + "    ]"
            + "  ],"
            + "  \"status_message\": \"Found route between points\","
            + "  \"status\": 0"
            + "}";
    }


    static String osrm5Response() {

        // output produced by query to
        // /route/v1/driving/8.691601,53.092984;8.733542,53.082857?overview=false&alternatives=false&steps=true
        return "{"
            + "  \"code\": \"Ok\","
            + "  \"routes\": ["
            + "    {"
            + "      \"legs\": ["
            + "        {"
            + "          \"summary\": \"A 281\\/;motorway\\/;yes\\/;DE, Senator-Apelt-Stra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "          \"duration\": 503.6,"
            + "          \"steps\": ["
            + "            {"
            + "              \"intersections\": ["
            + "                {"
            + "                  \"out\": 0,"
            + "                  \"entry\": ["
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    145"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.691601,"
            + "                    53.092984"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 0,"
            + "                  \"in\": 2,"
            + "                  \"entry\": ["
            + "                    true,"
            + "                    false,"
            + "                    false"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    120,"
            + "                    285,"
            + "                    300"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.742344,"
            + "                    53.076682"
            + "                  ]"
            + "                }"
            + "              ],"
            + "              \"geometry\": \"cv`cIoq`t@LOr@aA~BcEdAgCjAcD~@sD`A}DlE{RzJsd@zOys@zOws@vJyc@lCwLvBuLjB_L~BoOh@gDv@}EvCoQf@_DlCuPx@sFTsBPoCHkBBaDEgDU}Da@qE\","
            + "              \"maneuver\": {"
            + "                \"bearing_after\": 145,"
            + "                \"bearing_before\": 0,"
            + "                \"location\": ["
            + "                  8.691601,"
            + "                  53.092984"
            + "                ],"
            + "                \"type\": \"depart\""
            + "              },"
            + "              \"mode\": \"driving\","
            + "              \"duration\": 167.5,"
            + "              \"name\": \"A 281\\/;motorway\\/;yes\\/;DE\","
            + "              \"distance\": 4584.5"
            + "            },"
            + "            {"
            + "              \"intersections\": ["
            + "                {"
            + "                  \"out\": 1,"
            + "                  \"in\": 2,"
            + "                  \"entry\": ["
            + "                    true,"
            + "                    true,"
            + "                    false"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    75,"
            + "                    90,"
            + "                    255"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.752675,"
            + "                    53.075524"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 2,"
            + "                  \"in\": 1,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    false,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    120,"
            + "                    225,"
            + "                    315"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.757013,"
            + "                    53.075787"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 2,"
            + "                  \"in\": 1,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    false,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    90,"
            + "                    165,"
            + "                    345"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.756618,"
            + "                    53.0763"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 0,"
            + "                  \"in\": 1,"
            + "                  \"entry\": ["
            + "                    true,"
            + "                    false,"
            + "                    false"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    0,"
            + "                    180,"
            + "                    195"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.756556,"
            + "                    53.076577"
            + "                  ]"
            + "                }"
            + "              ],"
            + "              \"geometry\": \"_i}bIgolt@@k@MeBOqBE_BCmB?_CFaC@w@ASCUGSKWEHKVONMHODYHK@w@J}Gz@kAH_@?qAJo@FM@UB\","
            + "              \"maneuver\": {"
            + "                \"bearing_after\": 97,"
            + "                \"bearing_before\": 74,"
            + "                \"location\": ["
            + "                  8.752675,"
            + "                  53.075524"
            + "                ],"
            + "                \"modifier\": \"left\","
            + "                \"type\": \"turn\""
            + "              },"
            + "              \"mode\": \"driving\","
            + "              \"duration\": 24,"
            + "              \"name\": \"\\/;motorway_link\\/;yes\\/;DE\","
            + "              \"distance\": 706.3"
            + "            },"
            + "            {"
            + "              \"intersections\": ["
            + "                {"
            + "                  \"out\": 0,"
            + "                  \"in\": 2,"
            + "                  \"entry\": ["
            + "                    true,"
            + "                    true,"
            + "                    false,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    0,"
            + "                    90,"
            + "                    180,"
            + "                    255"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.756075,"
            + "                    53.07938"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 2,"
            + "                  \"in\": 0,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    false,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    165,"
            + "                    255,"
            + "                    345"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.755647,"
            + "                    53.08119"
            + "                  ]"
            + "                }"
            + "              ],"
            + "              \"geometry\": \"ca~bIodmt@yAPqC`@kB\\\\QBSH\","
            + "              \"maneuver\": {"
            + "                \"bearing_after\": 353,"
            + "                \"bearing_before\": 354,"
            + "                \"location\": ["
            + "                  8.756075,"
            + "                  53.07938"
            + "                ],"
            + "                \"modifier\": \"straight\","
            + "                \"type\": \"new name\""
            + "              },"
            + "              \"mode\": \"driving\","
            + "              \"duration\": 17.1,"
            + "              \"name\": \"Senator-Apelt-Stra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "              \"distance\": 214.4"
            + "            },"
            + "            {"
            + "              \"intersections\": ["
            + "                {"
            + "                  \"out\": 1,"
            + "                  \"in\": 0,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    true,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    165,"
            + "                    255,"
            + "                    345"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.755601,"
            + "                    53.081285"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 2,"
            + "                  \"in\": 0,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    true,"
            + "                    true,"
            + "                    false"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    90,"
            + "                    165,"
            + "                    255,"
            + "                    345"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.755415,"
            + "                    53.081271"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 2,"
            + "                  \"in\": 0,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    true,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    105,"
            + "                    195,"
            + "                    285"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.753912,"
            + "                    53.081214"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 2,"
            + "                  \"in\": 0,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    false,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    135,"
            + "                    270,"
            + "                    315"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.750639,"
            + "                    53.083579"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 3,"
            + "                  \"in\": 1,"
            + "                  \"entry\": ["
            + "                    true,"
            + "                    false,"
            + "                    true,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    45,"
            + "                    150,"
            + "                    210,"
            + "                    330"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.750347,"
            + "                    53.083808"
            + "                  ]"
            + "                },"
            + "                {"
            + "                  \"out\": 3,"
            + "                  \"in\": 1,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    false,"
            + "                    true,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    45,"
            + "                    150,"
            + "                    240,"
            + "                    315"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.748457,"
            + "                    53.085351"
            + "                  ]"
            + "                }"
            + "              ],"
            + "              \"geometry\": \"am~bIoamt@Bb@@`@HvB@l@?h@@FCl@?DGZOj@M^mBjCeFzG_B|Bm@x@[R[`@m@`Au@fAwCxDuAnBiB`CgAxAA@SXgJ|LUXQVYd@[x@UfAGh@C`@Ap@@l@F~@D`@b@fDtC~TD^p@vELdAr@pFb@pDNtA\","
            + "              \"maneuver\": {"
            + "                \"bearing_after\": 263,"
            + "                \"bearing_before\": 344,"
            + "                \"location\": ["
            + "                  8.755601,"
            + "                  53.081285"
            + "                ],"
            + "                \"modifier\": \"left\","
            + "                \"type\": \"continue\""
            + "              },"
            + "              \"mode\": \"driving\","
            + "              \"duration\": 1,"
            + "              \"name\": \"Senator-Apelt-Stra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "              \"distance\": 1945.2"
            + "            },"
            + "            {"
            + "              \"intersections\": ["
            + "                {"
            + "                  \"out\": 2,"
            + "                  \"in\": 0,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    true,"
            + "                    true,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    75,"
            + "                    165,"
            + "                    255,"
            + "                    345"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.73351,"
            + "                    53.087347"
            + "                  ]"
            + "                }"
            + "              ],"
            + "              \"geometry\": \"}r_cImwht@Hj@b@|DX~CXvCJt@Jl@Nf@P\\\\PXNNVPl@Vj@T`@R\","
            + "              \"maneuver\": {"
            + "                \"bearing_after\": 252,"
            + "                \"bearing_before\": 253,"
            + "                \"location\": ["
            + "                  8.73351,"
            + "                  53.087347"
            + "                ],"
            + "                \"modifier\": \"straight\","
            + "                \"type\": \"turn\""
            + "              },"
            + "              \"mode\": \"driving\","
            + "              \"duration\": 31.6,"
            + "              \"name\": \"Rudolf-Diesel-Stra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "              \"distance\": 366.9"
            + "            },"
            + "            {"
            + "              \"intersections\": ["
            + "                {"
            + "                  \"out\": 1,"
            + "                  \"in\": 0,"
            + "                  \"entry\": ["
            + "                    false,"
            + "                    true,"
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    15,"
            + "                    120,"
            + "                    300"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.729299,"
            + "                    53.085662"
            + "                  ]"
            + "                }"
            + "              ],"
            + "              \"geometry\": \"kh_cIc}gt@He@R{@p@gCZgATk@Zw@N[LUh@_Ap@}@fBgB`B{A`@Yj@k@\","
            + "              \"maneuver\": {"
            + "                \"bearing_after\": 117,"
            + "                \"bearing_before\": 200,"
            + "                \"location\": ["
            + "                  8.729299,"
            + "                  53.085662"
            + "                ],"
            + "                \"modifier\": \"left\","
            + "                \"type\": \"turn\""
            + "              },"
            + "              \"mode\": \"driving\","
            + "              \"duration\": 36.2,"
            + "              \"name\": \"Merkurstra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "              \"distance\": 432"
            + "            },"
            + "            {"
            + "              \"intersections\": ["
            + "                {"
            + "                  \"in\": 0,"
            + "                  \"entry\": ["
            + "                    true"
            + "                  ],"
            + "                  \"bearings\": ["
            + "                    330"
            + "                  ],"
            + "                  \"location\": ["
            + "                    8.733543,"
            + "                    53.082857"
            + "                  ]"
            + "                }"
            + "              ],"
            + "              \"geometry\": \"{v~bIswht@\","
            + "              \"maneuver\": {"
            + "                \"bearing_after\": 0,"
            + "                \"bearing_before\": 150,"
            + "                \"location\": ["
            + "                  8.729299,"
            + "                  53.085662"
            + "                ],"
            + "                \"type\": \"arrive\""
            + "              },"
            + "              \"mode\": \"driving\","
            + "              \"duration\": 0,"
            + "              \"name\": \"Merkurstra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "              \"distance\": 0"
            + "            }"
            + "          ],"
            + "          \"distance\": 8249.1"
            + "        }"
            + "      ],"
            + "      \"duration\": 503.6,"
            + "      \"distance\": 8249.1"
            + "    }"
            + "  ],"
            + "  \"waypoints\": ["
            + "    {"
            + "      \"hint\": \"o18AgP___39_AgAAEgAAABcAAAASAAAAFwAAALkAAACGBgAAfQgAANYHAABRAAAAkZ-EAHgiKgORn4QAeCIqAwkAAQGxmR_Z\","
            + "      \"name\": \"A 281\\/;motorway\\/;yes\\/;DE\","
            + "      \"location\": ["
            + "        8.691601,"
            + "        53.092984"
            + "      ]"
            + "    },"
            + "    {"
            + "      \"hint\": \"vFYAgP___3-nJQAAGAAAABwAAAAYAAAAHAAAABIAAAByAAAAbCMAAG0jAABRAAAAZ0OFAOn6KQNmQ4UA6fopAwEAAQGxmR_Z\","
            + "      \"name\": \"Merkurstra\\u00dfe\\/;tertiary\\/;no\\/;DE\","
            + "      \"location\": ["
            + "        8.733543,"
            + "        53.082857"
            + "      ]"
            + "    }"
            + "  ]"
            + "}";
    }


    static String osrm5ErrorResponse() {

        return "{"
            + "  \"message\": \"Impossible route between points\","
            + "  \"code\": \"NoRoute\""
            + "}";
    }
}
