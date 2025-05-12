--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: mantenimiento; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA mantenimiento;


ALTER SCHEMA mantenimiento OWNER TO postgres;

--
-- Name: operaciones; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA operaciones;


ALTER SCHEMA operaciones OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: tab_cliente; Type: TABLE; Schema: mantenimiento; Owner: postgres
--

CREATE TABLE mantenimiento.tab_cliente (
    id_cliente bigint NOT NULL,
    fecha_actualizacion timestamp(6) without time zone,
    fecha_registro timestamp(6) without time zone,
    ip character varying(255),
    observacion character varying(255),
    direccion character varying(255),
    edad character varying(255),
    genero character varying(255),
    identificacion character varying(255),
    nombre character varying(255),
    telefono character varying(255),
    contrasena character varying(255),
    estado boolean,
    usuario character varying(255)
);


ALTER TABLE mantenimiento.tab_cliente OWNER TO postgres;

--
-- Name: tab_cuenta; Type: TABLE; Schema: operaciones; Owner: postgres
--

CREATE TABLE operaciones.tab_cuenta (
    id_cuenta bigint NOT NULL,
    fecha_actualizacion timestamp(6) without time zone,
    fecha_registro timestamp(6) without time zone,
    ip character varying(255),
    observacion character varying(255),
    estado boolean,
    numero_cuenta character varying(255),
    saldo_inicial numeric(38,2),
    tipo_cuenta character varying(255),
    identificacion_cliente character varying(10),
    nombre_cliente character varying(100),
    saldo_disponible numeric(38,2)
);


ALTER TABLE operaciones.tab_cuenta OWNER TO postgres;

--
-- Name: tab_movimientos; Type: TABLE; Schema: operaciones; Owner: postgres
--

CREATE TABLE operaciones.tab_movimientos (
    id_movimiento bigint NOT NULL,
    fecha_actualizacion timestamp(6) without time zone,
    fecha_registro timestamp(6) without time zone,
    ip character varying(255),
    observacion character varying(255),
    estado boolean,
    fecha_movimiento timestamp(6) without time zone,
    movimiento character varying(255),
    numero_cuenta character varying(255),
    saldo numeric(38,2),
    valor_movimiento numeric(38,2),
    id_cuenta bigint
);


ALTER TABLE operaciones.tab_movimientos OWNER TO postgres;

--
-- Name: sec_cliente; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sec_cliente
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sec_cliente OWNER TO postgres;

--
-- Name: sec_cuenta; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sec_cuenta
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sec_cuenta OWNER TO postgres;

--
-- Name: sec_movimiento; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sec_movimiento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sec_movimiento OWNER TO postgres;

--
-- Name: sec_persona; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sec_persona
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sec_persona OWNER TO postgres;

--
-- Data for Name: tab_cliente; Type: TABLE DATA; Schema: mantenimiento; Owner: postgres
--

COPY mantenimiento.tab_cliente (id_cliente, fecha_actualizacion, fecha_registro, ip, observacion, direccion, edad, genero, identificacion, nombre, telefono, contrasena, estado, usuario) FROM stdin;
47	\N	2025-05-11 14:28:05.57	127.0.1.1	REGISTRO CLIENTE	CALLE MANABÍ Y LOJA	60	M	0945678901	LUIS MENDOZA	0954321789	59841	t	0945678901
48	\N	2025-05-11 14:31:28.115	127.0.1.1	REGISTRO CLIENTE	MUCHO LOTE ET 4	23	M	0912320258	FABIAN VILLAGRAN	0952356120	78328	t	0912320258
49	\N	2025-05-11 14:31:35.455	127.0.1.1	REGISTRO CLIENTE	AV. QUITO Y COLOMBIA	52	M	0978256410	PEDRO VELA	0992351258	56433	t	0978256410
50	\N	2025-05-11 14:31:42.518	127.0.1.1	REGISTRO CLIENTE	CALLE 10 DE AGOSTO Y BOLÍVAR	45	F	0912345678	MARÍA LOPEZ	0987654321	01132	t	0912345678
51	\N	2025-05-11 14:31:49.729	127.0.1.1	REGISTRO CLIENTE	AV. DEL EJÉRCITO Y CHILE	38	M	0923456789	CARLOS JIMENEZ	0971122334	46752	t	0923456789
52	\N	2025-05-11 14:31:56.503	127.0.1.1	REGISTRO CLIENTE	URB. LOS ALMENDROS, BLOQUE 3	29	F	0934567890	ANA TORRES	0961234567	13190	t	0934567890
\.


--
-- Data for Name: tab_cuenta; Type: TABLE DATA; Schema: operaciones; Owner: postgres
--

COPY operaciones.tab_cuenta (id_cuenta, fecha_actualizacion, fecha_registro, ip, observacion, estado, numero_cuenta, saldo_inicial, tipo_cuenta, identificacion_cliente, nombre_cliente, saldo_disponible) FROM stdin;
27	\N	2025-05-11 20:22:16.651	127.0.1.1	CREACION CUENTA	t	9205009671	10.00	AHORROS	0945678901	PEDRO VELA	10.00
28	\N	2025-05-11 20:23:49.231	127.0.1.1	CREACION CUENTA	t	2392814074	10.00	AHORROS	0945678901	PEDRO VELA	10.00
26	\N	2025-05-11 17:41:08.767	127.0.1.1	CREACION CUENTA	t	4818894541	10.00	AHORROS	0945678901	PEDRO VELA	809.00
\.


--
-- Data for Name: tab_movimientos; Type: TABLE DATA; Schema: operaciones; Owner: postgres
--

COPY operaciones.tab_movimientos (id_movimiento, fecha_actualizacion, fecha_registro, ip, observacion, estado, fecha_movimiento, movimiento, numero_cuenta, saldo, valor_movimiento, id_cuenta) FROM stdin;
47	\N	2025-05-11 17:42:06.155	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 17:42:06.155	DEPOSITO valor 20.00	4818894541	30.00	20.00	26
48	\N	2025-05-11 17:42:51.084	127.0.1.1	CREACION RETIRO	t	2025-05-11 17:42:51.084	RETIRO valor 1.00	4818894541	29.00	1.00	26
49	\N	2025-05-11 17:43:46.722	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 17:43:46.722	DEPOSITO valor 100.00	4818894541	129.00	100.00	26
50	\N	2025-05-11 17:44:44.3	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 17:44:44.3	DEPOSITO valor -100.00	4818894541	29.00	-100.00	26
51	\N	2025-05-11 17:56:45.593	127.0.1.1	CREACION RETIRO	t	2025-05-11 17:56:45.593	RETIRO valor 10.00	4818894541	19.00	-10.00	26
52	\N	2025-05-11 20:20:49.852	127.0.1.1	CREACION RETIRO	t	2025-05-11 20:20:49.851	RETIRO valor 10.00	4818894541	9.00	10.00	26
53	\N	2025-05-11 20:21:17.472	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 20:21:17.472	DEPOSITO valor 100.00	4818894541	109.00	100.00	26
54	\N	2025-05-11 20:22:37.844	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 20:22:37.844	DEPOSITO valor 100.00	4818894541	209.00	100.00	26
55	\N	2025-05-11 20:22:39.214	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 20:22:39.214	DEPOSITO valor 100.00	4818894541	309.00	100.00	26
56	\N	2025-05-11 20:22:40.008	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 20:22:40.008	DEPOSITO valor 100.00	4818894541	409.00	100.00	26
57	\N	2025-05-11 20:22:41.151	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 20:22:41.151	DEPOSITO valor 100.00	4818894541	509.00	100.00	26
58	\N	2025-05-11 20:22:41.622	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 20:22:41.622	DEPOSITO valor 100.00	4818894541	609.00	100.00	26
59	\N	2025-05-11 20:22:43.096	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 20:22:43.096	DEPOSITO valor 100.00	4818894541	709.00	100.00	26
60	\N	2025-05-11 20:23:55.363	127.0.1.1	CREACION DEPOSITO	t	2025-05-11 20:23:55.363	DEPOSITO valor 100.00	4818894541	809.00	100.00	26
\.


--
-- Name: sec_cliente; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sec_cliente', 52, true);


--
-- Name: sec_cuenta; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sec_cuenta', 28, true);


--
-- Name: sec_movimiento; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sec_movimiento', 60, true);


--
-- Name: sec_persona; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sec_persona', 12, true);


--
-- Name: tab_cliente tab_cliente_pkey; Type: CONSTRAINT; Schema: mantenimiento; Owner: postgres
--

ALTER TABLE ONLY mantenimiento.tab_cliente
    ADD CONSTRAINT tab_cliente_pkey PRIMARY KEY (id_cliente);


--
-- Name: tab_cuenta tab_cuenta_pkey; Type: CONSTRAINT; Schema: operaciones; Owner: postgres
--

ALTER TABLE ONLY operaciones.tab_cuenta
    ADD CONSTRAINT tab_cuenta_pkey PRIMARY KEY (id_cuenta);


--
-- Name: tab_movimientos tab_movimientos_pkey; Type: CONSTRAINT; Schema: operaciones; Owner: postgres
--

ALTER TABLE ONLY operaciones.tab_movimientos
    ADD CONSTRAINT tab_movimientos_pkey PRIMARY KEY (id_movimiento);


--
-- Name: tab_movimientos fkmxxun8x10pkedxtcionpul9g0; Type: FK CONSTRAINT; Schema: operaciones; Owner: postgres
--

ALTER TABLE ONLY operaciones.tab_movimientos
    ADD CONSTRAINT fkmxxun8x10pkedxtcionpul9g0 FOREIGN KEY (id_cuenta) REFERENCES operaciones.tab_cuenta(id_cuenta);


--
-- PostgreSQL database dump complete
--

