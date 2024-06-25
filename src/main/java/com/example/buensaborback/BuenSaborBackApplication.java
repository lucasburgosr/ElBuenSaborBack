package com.example.buensaborback;

import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.domain.enums.*;
import com.example.buensaborback.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication(scanBasePackages = "com.example.buensaborback")
public class BuenSaborBackApplication {
// Aca tiene que inyectar todos los repositorios
// Es por ello que deben crear el paquete reositorio

// Ejemplo  @Autowired
//	private ClienteRepository clienteRepository;

	private static final Logger logger = LoggerFactory.getLogger(BuenSaborBackApplication.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Autowired
	private LocalidadRepository localidadRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private SucursalRepository	sucursalRepository;

	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;

	@Autowired
	private ArticuloInsumoRepository articuloInsumoRepository;

	@Autowired
	private ArticuloManufacturadoRepository articuloManufacturadoRepository;

	@Autowired
	private PromocionRepository promocionRepository;

	@Autowired
	private  PedidoRepository pedidoRepository;

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	private UsuarioClienteRepository usuarioClienteRepository;

	@Autowired
	private UsuarioEmpleadoRepository usuarioEmpleadoRepository;


	public static void main(String[] args) {
		SpringApplication.run(BuenSaborBackApplication.class, args);
		logger.info("Me ejecutaste");
		System.out.println("jdbc:h2:mem:testdb");
		System.out.println("http://localhost:8080/h2-console/");
		System.out.println("SELECT * FROM UNIDAD_MEDIDA ;\n" +
				"SELECT * FROM PAIS ;\n" +
				"SELECT * FROM PROVINCIA ;\n" +
				"SELECT * FROM LOCALIDAD ;\n" +
				"SELECT * FROM DOMICILIO ;\n" +
				"SELECT * FROM EMPRESA ;\n" +
				"SELECT * FROM USUARIO_EMPLEADO ;\n" +
				"SELECT * FROM EMPLEADO ;\n" +
				"SELECT * FROM IMAGEN_EMPLEADO ;\n" +
				"SELECT * FROM SUCURSAL ;\n" +
				"SELECT * FROM SUCURSAL_PROMOCION ;\n" +
				"SELECT * FROM SUCURSAL_CATEGORIA ;\n" +
				"SELECT * FROM CATEGORIA ;\n" +
				"SELECT * FROM IMAGEN_ARTICULO ;\n" +
				"SELECT * FROM ARTICULO_INSUMO;\n" +
				"SELECT * FROM ARTICULO_MANUFACTURADO ;\n" +
				"SELECT * FROM ARTICULO_MANUFACTURADO_DETALLE;\n" +
				"SELECT * FROM IMAGEN_PROMOCION ;\n" +
				"SELECT * FROM PROMOCION ;\n" +
				"SELECT * FROM PROMOCION_DETALLE ;\n" +
				"SELECT * FROM USUARIO_CLIENTE ;\n" +
				"SELECT * FROM CLIENTE;\n" +
				"SELECT * FROM IMAGEN_CLIENTE ;\n" +
				"SELECT * FROM CLIENTE_DOMICILIO ;\n" +
				"SELECT * FROM PEDIDO ;\n" +
				"SELECT * FROM DETALLE_PEDIDO ;\n" +
				"SELECT * FROM FACTURA ;");
		System.out.println("http://localhost:8080/swagger-ui/index.html");
	}


	@Bean
	CommandLineRunner init() {
		return args -> {
			logger.info("----------------ESTOY----FUNCIONANDO---------------------");
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject("https://infra.datos.gob.ar/georef/departamentos.json", String.class);
			JSONObject jsonObject = new JSONObject(jsonResponse);
			JSONArray departamentosArray = jsonObject.getJSONArray("departamentos");

			// Etapa del dashboard
			// Crear 1 pais
			// Crear 2 provincias para ese pais
			// crear 2 localidades para cada provincia
			Pais pais1 = Pais.builder().nombre("Argentina").build();
			logger.info("Pais {}",pais1);
			Provincia provincia1 = Provincia.builder().nombre("Mendoza").pais(pais1).build();
			Provincia provincia2 = Provincia.builder().nombre("Cordoba").pais(pais1).build();

			// Creación de provincias
			logger.info("Provincia {}",provincia1);
			logger.info("Provincia {}",provincia2);
			Localidad localidad1 = Localidad.builder().nombre("Lujan de Cuyo").provincia(provincia1).build();
			Localidad localidad2 = Localidad.builder().nombre("Godoy Cruz").provincia(provincia1).build();
			Localidad localidad3 = Localidad.builder().nombre("Achiras").provincia(provincia2).build();
			Localidad localidad4 = Localidad.builder().nombre("Agua de Oro").provincia(provincia2).build();
			localidadRepository.saveAll(Set.of(localidad1, localidad2, localidad3, localidad4));

			Pais pais = paisRepository.findById(1L).orElseGet(() -> {
				Pais newPais = new Pais();
				newPais.setId(1L);
				newPais.setNombre("Argentina");
				return paisRepository.save(newPais);
			});

			departamentosArray.forEach(obj -> {
				JSONObject departamentoJson = (JSONObject) obj;

				Long localidadId = Long.parseLong(departamentoJson.getString("id"));
				String localidadNombre = departamentoJson.getString("nombre");

				JSONObject provinciaJson = departamentoJson.getJSONObject("provincia");
				Long provinciaId = Long.parseLong(provinciaJson.getString("id"));
				String provinciaNombre = provinciaJson.getString("nombre");

				// Verificar si la provincia ya existe por nombre, si no, crearla y guardarla
//				Provincia provincia = provinciaRepository.findByNombre(provinciaNombre);
//				if (provincia == null) {
//					provincia = new Provincia();
//					provincia.setId(provinciaId);
//					provincia.setNombre(provinciaNombre);
//					provincia.setPais(pais);
//					provincia = provinciaRepository.save(provincia);
//				}

//				Localidad localidad = localidadRepository.findByNombre(localidadNombre);
//				if (localidad == null) {
//					localidad = new Localidad();
//					localidad.setId(localidadId);
//					localidad.setNombre(localidadNombre);
//					localidad.setProvincia(provincia);
//					localidadRepository.save(localidad);
//				}
			});

			// Crear 1 empresa
			ImagenEmpresa imagenEmpresa = ImagenEmpresa.builder().url("https://economipedia.com/wp-content/uploads/Empresa-1.png").build();
			Empresa empresaBrown = Empresa.builder().nombre("Lo de Brown").cuil(30503167).imagen(imagenEmpresa).razonSocial("Venta de Alimentos").build();
//			empresaBrown.getSucursales().add(sucursalChacras);
//			empresaBrown.getSucursales().add(sucursalGodoyCruz);
			empresaRepository.save(empresaBrown);
			logger.info("Empresa {}", empresaBrown);


			// Crear 2 sucursales para esa empresa
			Sucursal sucursalChacras = Sucursal.builder().nombre("En chacras").casaMatriz(true).empresa(empresaBrown).horarioApertura(LocalTime.of(17,0)).horarioCierre(LocalTime.of(23,0)).build();
			Domicilio domicilioViamonte = Domicilio.builder().cp(5509).calle("Viamonte").numero(500).localidad(localidad1).build();
			sucursalChacras.setDomicilio(domicilioViamonte);
			logger.info("Sucursal {}",sucursalChacras);

			Sucursal sucursalGodoyCruz = Sucursal.builder().nombre("En godoy cruz").casaMatriz(false).empresa(empresaBrown).horarioApertura(LocalTime.of(16,0)).horarioCierre(LocalTime.of(23,30)).build();
			Domicilio domicilioSanMartin = Domicilio.builder().cp(5511).calle("San Martin").numero(789).localidad(localidad2).build();
			sucursalGodoyCruz.setDomicilio(domicilioSanMartin);
			logger.info("Sucursal {}",sucursalGodoyCruz);


			Categoria categoriaPizzas = Categoria.builder().denominacion("Pizzas").sucursales(Set.of(sucursalChacras,sucursalGodoyCruz)).build();

			logger.info("Categoría {}", categoriaPizzas);

			Categoria categoriaInsumos = Categoria.builder().denominacion("Insumos").sucursales(Set.of(sucursalChacras,sucursalGodoyCruz)).build();


			logger.info("Categoría {}", categoriaInsumos);

			Categoria categoriaBebidas = Categoria.builder().denominacion("Bebidas").sucursales(Set.of(sucursalChacras,sucursalGodoyCruz)).build();
			Categoria categoriaTragos = Categoria.builder().denominacion("Tragos").categoriaPadre(categoriaBebidas).sucursales(Set.of(sucursalChacras,sucursalGodoyCruz)).build();
			categoriaBebidas.getSubCategorias().add(categoriaTragos);
			Categoria categoriaGaseosas = Categoria.builder().denominacion("Gaseosas").categoriaPadre(categoriaBebidas).sucursales(Set.of(sucursalChacras,sucursalGodoyCruz)).build();


			logger.info("Categoría {}", categoriaBebidas);
			sucursalChacras.setCategorias(Set.of( categoriaBebidas,categoriaGaseosas,categoriaTragos,categoriaPizzas,categoriaInsumos));
			sucursalGodoyCruz.setCategorias(Set.of( categoriaBebidas,categoriaGaseosas,categoriaTragos,categoriaPizzas,categoriaInsumos));

			sucursalRepository.saveAll(Set.of(sucursalChacras, sucursalGodoyCruz));

			UnidadMedida unidadMedidaLitros = UnidadMedida.builder().denominacion("Litros").build();
			UnidadMedida unidadMedidaGramos = UnidadMedida.builder().denominacion("Gramos").build();
			UnidadMedida unidadMedidaCantidad = UnidadMedida.builder().denominacion("Cantidad").build();
			UnidadMedida unidadMedidaPorciones = UnidadMedida.builder().denominacion("Porciones").build();
			unidadMedidaRepository.save(unidadMedidaLitros);
			logger.info("UnidadMedida {}",unidadMedidaLitros);
			unidadMedidaRepository.save(unidadMedidaGramos);
			logger.info("UnidadMedida {}",unidadMedidaGramos);
			unidadMedidaRepository.save(unidadMedidaCantidad);
			logger.info("UnidadMedida {}",unidadMedidaCantidad);
			unidadMedidaRepository.save(unidadMedidaPorciones);
			logger.info("UnidadMedida {}",unidadMedidaPorciones);

			// Crear Unidades de medida
			List<String> denominaciones = List.of(
					"Mililitros", "Centilitros", "Metros cúbicos", "Centímetros cúbicos", "Kilogramos",
					"Metros", "Centímetros", "Milímetros", "Metros cuadrados", "Porciones", "Paquete",
					"Docena"
			);

			Set<UnidadMedida> unidadesMedida = denominaciones.stream()
					.map(denominacion -> UnidadMedida.builder().denominacion(denominacion).build())
					.collect(Collectors.toSet());

			unidadMedidaRepository.saveAll(unidadesMedida);

			unidadesMedida.forEach(unidadMedida -> logger.info("UnidadMedida {}", unidadMedida));

			//Crear Insumos , coca cola , harina , etc
			ArticuloInsumo cocaCola = ArticuloInsumo.builder().denominacion("Coca cola").unidadMedida(unidadMedidaLitros).esParaElaborar(false).categoria(categoriaGaseosas).stockActual(5).stockMinimo(25).stockMaximo(50).precioCompra(50.0).precioVenta(70.0).build();
			ImagenArticulo imagenCoca = ImagenArticulo.builder().url("https://m.media-amazon.com/images/I/51v8nyxSOYL._SL1500_.jpg").articulo(cocaCola).build();
			cocaCola.getImagenes().add(imagenCoca);
			logger.info("Insumo {}", cocaCola);

			ArticuloInsumo harina = ArticuloInsumo.builder().denominacion("Harina").unidadMedida(unidadMedidaGramos).esParaElaborar(true).categoria(categoriaInsumos).stockActual(4).stockMinimo(25).stockMaximo(40).precioCompra(40.0).precioVenta(60.5).build();
			ImagenArticulo imagenHarina = ImagenArticulo.builder().url("https://mandolina.co/wp-content/uploads/2023/03/648366622-1024x683.jpg").articulo(harina).build();
			harina.getImagenes().add(imagenHarina);
			logger.info("Insumo {}", harina);

			ArticuloInsumo tomate = ArticuloInsumo.builder().denominacion("Tomate").unidadMedida(unidadMedidaCantidad).esParaElaborar(true).categoria(categoriaInsumos).stockActual(20).stockMinimo(25).stockMaximo(50).precioCompra(23.6).precioVenta(66.6).build();
			ImagenArticulo imagenTomate = ImagenArticulo.builder().url("https://thefoodtech.com/wp-content/uploads/2020/06/Componentes-de-calidad-en-el-tomate-828x548.jpg").articulo(tomate).build();
			tomate.getImagenes().add(imagenTomate);
			logger.info("Insumo {}", tomate);

			ArticuloInsumo queso = ArticuloInsumo.builder().denominacion("Queso").unidadMedida(unidadMedidaGramos).esParaElaborar(true).categoria(categoriaInsumos).stockActual(20).stockMinimo(25).stockMaximo(50).precioCompra(23.6).precioVenta(66.6).build();
			ImagenArticulo imagenQueso = ImagenArticulo.builder().url("https://superdepaso.com.ar/wp-content/uploads/2021/06/SANTAROSA-PATEGRAS-04.jpg").articulo(queso).build();
			queso.getImagenes().add(imagenQueso);
			logger.info("Insumo {}", queso);

			// Crear Articulos Manufacturados
			ArticuloManufacturado pizzaMuzarella = ArticuloManufacturado.builder().denominacion("Pizza Muzarella").descripcion("Una pizza clasica").unidadMedida(unidadMedidaPorciones).precioVenta(130.0).tiempoEstimadoMinutos(15).preparacion("Esto se prepara asi").categoria(categoriaPizzas).stock(20).precioCosto(100.0).build();
			ImagenArticulo imagenPizzaMuzarella = ImagenArticulo.builder().url("https://storage.googleapis.com/fitia-api-bucket/media/images/recipe_images/1002846.jpg").articulo(pizzaMuzarella).build();
			pizzaMuzarella.getImagenes().add(imagenPizzaMuzarella);

			ArticuloManufacturadoDetalle detalleHarinaPizzaMuzarella = ArticuloManufacturadoDetalle.builder().cantidad(1d).articuloInsumo(harina).articuloManufacturado(pizzaMuzarella).build();
			ArticuloManufacturadoDetalle detalleQuesoPizzaMuzarella = ArticuloManufacturadoDetalle.builder().cantidad(2d).articuloInsumo(queso).articuloManufacturado(pizzaMuzarella).build();
			pizzaMuzarella.setArticuloManufacturadoDetalles(Set.of(detalleHarinaPizzaMuzarella,detalleQuesoPizzaMuzarella));

			logger.info("Manufacturado {}", pizzaMuzarella);

			ArticuloManufacturado pizzaNapolitana = ArticuloManufacturado.builder().denominacion("Pizza Napolitana").descripcion("Una pizza clasica con tomate").unidadMedida(unidadMedidaPorciones).precioVenta(150.0).tiempoEstimadoMinutos(15).preparacion("Esto se prepara asi").categoria(categoriaPizzas).stock(20).precioCosto(100.0).build();
			ImagenArticulo imagenPizzaNapolitana = ImagenArticulo.builder().url("https://assets.elgourmet.com/wp-content/uploads/2023/03/8metlvp345_portada-pizza-1024x686.jpg.webp").articulo(pizzaNapolitana).build();
			pizzaNapolitana.getImagenes().add(imagenPizzaNapolitana);
			ArticuloManufacturadoDetalle detalleHarinaPizzaNapolatina = ArticuloManufacturadoDetalle.builder().cantidad(3d).articuloInsumo(harina).articuloManufacturado(pizzaNapolitana).build();
			ArticuloManufacturadoDetalle detalleQuesoPizzaNapolatina = ArticuloManufacturadoDetalle.builder().cantidad(1d).articuloInsumo(queso).articuloManufacturado(pizzaNapolitana).build();
			ArticuloManufacturadoDetalle detalleTomatePizzaNapolatina = ArticuloManufacturadoDetalle.builder().cantidad(2d).articuloInsumo(tomate).articuloManufacturado(pizzaNapolitana).build();
			pizzaNapolitana.getArticuloManufacturadoDetalles().add(detalleHarinaPizzaNapolatina);
			pizzaNapolitana.getArticuloManufacturadoDetalles().add(detalleQuesoPizzaNapolatina);
			pizzaNapolitana.getArticuloManufacturadoDetalles().add(detalleTomatePizzaNapolatina);

			logger.info("Manufacturado {}", pizzaNapolitana);

			// Crear promocion para sucursal - Dia de los enamorados
			// Tener en cuenta que esa promocion es exclusivamente para una sucursal determinada d euna empresa determinada
			Promocion promocionDiaEnamorados = Promocion.builder().denominacion("Dia de los Enamorados")
					.fechaDesde(LocalDate.of(2024,2,13))
					.fechaHasta(LocalDate.of(2024,2,15))
					.horaDesde(LocalTime.of(0,0))
					.horaHasta(LocalTime.of(23,59))
					.descripcionDescuento("14 de febrero es el día de los enamorados")
					.precioPromocional(180d)
					.sucursales(Set.of(sucursalChacras))
					.tipoPromocion(TipoPromocion.Promocion)
					.build();
			PromocionDetalle promocionDetalleEnamoradosCocaCola = PromocionDetalle.builder().articulo(cocaCola).cantidad(1).promocion(promocionDiaEnamorados).build();
			PromocionDetalle promocionDetalleEnamoradosNapolitana = PromocionDetalle.builder().articulo(pizzaNapolitana).cantidad(2).promocion(promocionDiaEnamorados).build();
			promocionDiaEnamorados.getPromocionDetalles().add(promocionDetalleEnamoradosCocaCola);
			promocionDiaEnamorados.getPromocionDetalles().add(promocionDetalleEnamoradosNapolitana);
			ImagenPromocion imagenPromocionEnamorados = ImagenPromocion.builder().url("https://www.bbva.com/wp-content/uploads/2021/02/san-valentin-14-febrero-corazon-amor-bbva-recurso-1920x1280-min.jpg").promocion(promocionDiaEnamorados).build();
			promocionDiaEnamorados.getImagenes().add(imagenPromocionEnamorados);
			sucursalChacras.setPromociones(Set.of( promocionDiaEnamorados ));


			articuloInsumoRepository.save(cocaCola);
			articuloInsumoRepository.save(harina);
			articuloInsumoRepository.save(tomate);
			articuloInsumoRepository.save(queso);
			articuloManufacturadoRepository.save(pizzaMuzarella);
			articuloManufacturadoRepository.save(pizzaNapolitana);
			promocionRepository.save(promocionDiaEnamorados);
			sucursalRepository.saveAll(Set.of(sucursalChacras, sucursalGodoyCruz));

			logger.info("Promocion {}", promocionDiaEnamorados);

			//Creamos un CAJERO

			// Crear el UsuarioEmpleado sin establecer la relación con Empleado
			UsuarioEmpleado usuarioCajero = UsuarioEmpleado.builder()
					.auth0Id("auth0|66790fc261b261f643a861da")
					.username("cajero@gmail.com")
					.build();

			// Guardar el UsuarioEmpleado primero
			usuarioCajero = usuarioEmpleadoRepository.save(usuarioCajero);

			// Crear el Empleado sin establecer la relación con UsuarioEmpleado
			Empleado cajero = Empleado.builder()
					.pedidos(new HashSet())
					.nombre("Cajero")
					.apellido("1")
					.telefono("2610000000")
					.email("cajero@gmail.com")
					.rol(Rol.CAJERO)
					.fechaNacimiento(LocalDate.now())
					.imagen(null)
					.sucursal(sucursalChacras)
					.build();

			// Guardar el Empleado
			cajero = empleadoRepository.save(cajero);

			// Establecer las relaciones bidireccionales
			usuarioCajero.setEmpleado(cajero);
			cajero.setUsuario(usuarioCajero);

			// Guardar nuevamente las entidades actualizadas
			usuarioEmpleadoRepository.save(usuarioCajero);
			empleadoRepository.save(cajero);

			//Creamos un COCINERO

			// Crear el UsuarioEmpleado sin establecer la relación con Empleado
			UsuarioEmpleado usuarioCocinero = UsuarioEmpleado.builder()
					.auth0Id("auth0|667910084dc2a2f7799c05f0")
					.username("cocinero@gmail.com")
					.build();

			// Guardar el UsuarioEmpleado primero
			usuarioCocinero = usuarioEmpleadoRepository.save(usuarioCocinero);

			// Crear el Empleado sin establecer la relación con UsuarioEmpleado
			Empleado cocinero = Empleado.builder()
					.pedidos(new HashSet())
					.nombre("Cocinero")
					.apellido("1")
					.telefono("2610000000")
					.email("cocinero@gmail.com")
					.rol(Rol.COCINERO)
					.fechaNacimiento(LocalDate.now())
					.imagen(null)
					.sucursal(sucursalGodoyCruz)
					.build();

			// Guardar el Empleado
			cocinero = empleadoRepository.save(cocinero);

			// Establecer las relaciones bidireccionales
			usuarioCocinero.setEmpleado(cocinero);
			cocinero.setUsuario(usuarioCocinero);

			// Guardar nuevamente las entidades actualizadas
			usuarioEmpleadoRepository.save(usuarioCocinero);
			empleadoRepository.save(cocinero);

			//Creamos un ADMINISTRADOR

			// Crear el UsuarioEmpleado sin establecer la relación con Empleado
			UsuarioEmpleado usuarioAdministrador = UsuarioEmpleado.builder()
					.auth0Id("auth0|6679104dbe6f781304eac475")
					.username("administrador@gmail.com")
					.build();

			// Guardar el UsuarioEmpleado primero
			usuarioAdministrador = usuarioEmpleadoRepository.save(usuarioAdministrador);

			// Crear el Empleado sin establecer la relación con UsuarioEmpleado
			Empleado administrador = Empleado.builder()
					.pedidos(new HashSet())
					.nombre("Administrador")
					.apellido("1")
					.telefono("2610000000")
					.email("administrador@gmail.com")
					.rol(Rol.ADMIN)
					.fechaNacimiento(LocalDate.now())
					.imagen(null)
					.sucursal(sucursalChacras)
					.build();

			// Guardar el Empleado
			administrador = empleadoRepository.save(administrador);

			// Establecer las relaciones bidireccionales
			usuarioAdministrador.setEmpleado(administrador);
			administrador.setUsuario(usuarioAdministrador);

			// Guardar nuevamente las entidades actualizadas
			usuarioEmpleadoRepository.save(usuarioAdministrador);
			empleadoRepository.save(administrador);

			//Creamos un DELIVERY

			// Crear el UsuarioEmpleado sin establecer la relación con Empleado
			UsuarioEmpleado usuarioDelivery = UsuarioEmpleado.builder()
					.auth0Id("auth0|66791084a2cb38f879d75ccc")
					.username("delivery@gmail.com")
					.build();

			// Guardar el UsuarioEmpleado primero
			usuarioDelivery = usuarioEmpleadoRepository.save(usuarioDelivery);

			// Crear el Empleado sin establecer la relación con UsuarioEmpleado
			Empleado delivery = Empleado.builder()
					.pedidos(new HashSet())
					.nombre("Delivery")
					.apellido("1")
					.telefono("2610000000")
					.email("delivery@gmail.com")
					.rol(Rol.DELIVERY)
					.fechaNacimiento(LocalDate.now())
					.imagen(null)
					.sucursal(sucursalGodoyCruz)
					.build();

			// Guardar el Empleado
			delivery = empleadoRepository.save(delivery);

			// Establecer las relaciones bidireccionales
			usuarioDelivery.setEmpleado(delivery);
			delivery.setUsuario(usuarioDelivery);

			// Guardar nuevamente las entidades actualizadas
			usuarioEmpleadoRepository.save(usuarioDelivery);
			empleadoRepository.save(delivery);

			//Creamos un SUPERADMINISTRADOR

			// Crear el UsuarioEmpleado sin establecer la relación con Empleado
			UsuarioEmpleado usuarioSuper = UsuarioEmpleado.builder()
					.auth0Id("auth0|667910b301de4d750dd28a5e")
					.username("superadministrador@gmail.com")
					.build();

			// Guardar el UsuarioEmpleado primero
			usuarioSuper = usuarioEmpleadoRepository.save(usuarioSuper);

			// Crear el Empleado sin establecer la relación con UsuarioEmpleado
			Empleado superadministrador = Empleado.builder()
					.pedidos(new HashSet())
					.nombre("Superadmin")
					.apellido("1")
					.telefono("2610000000")
					.email("superadministrador@gmail.com")
					.rol(Rol.SUPERADMIN)
					.fechaNacimiento(LocalDate.now())
					.imagen(null)
					.sucursal(sucursalChacras)
					.build();

			// Guardar el Empleado
			superadministrador = empleadoRepository.save(superadministrador);

			// Establecer las relaciones bidireccionales
			usuarioSuper.setEmpleado(superadministrador);
			superadministrador.setUsuario(usuarioSuper);

			// Guardar nuevamente las entidades actualizadas
			usuarioEmpleadoRepository.save(usuarioSuper);
			empleadoRepository.save(superadministrador);

			// agregar pedido

//			Pedido pedido = Pedido.builder()
//					.domicilio(domicilioCliente1)
//					.estado(Estado.Entregado)
//					.formaPago(FormaPago.MercadoPago)
//					.fechaPedido(LocalDate.of(2024, 4, 18))
//					.horaEstimadaFinalizacion(LocalTime.of(12, 30))
//					.sucursal(sucursalChacras)
//					.tipoEnvio(TipoEnvio.Delivery)
//					.total(200d)
//					.totalCosto(180d)
//					.cliente(cliente1)
//					.empleado(empleadoCajero)
//					.build();
//			DetallePedido detallePedido1 = DetallePedido.builder().articulo(pizzaMuzarella).cantidad(1).subTotal(130d).pedido(pedido).build();
//			DetallePedido detallePedido2 = DetallePedido.builder().articulo(cocaCola).cantidad(1).subTotal(70d).pedido(pedido).build();
//			pedido.getDetallePedidos().add(detallePedido1);
//			pedido.getDetallePedidos().add(detallePedido2);
//
//			Factura factura = Factura.builder().fechaFacturacion(LocalDate.of(2024, 2, 13)).formaPago(FormaPago.MercadoPago).mpMerchantOrderId(1).mpPaymentId(1).mpPaymentType("mercado pago").mpPreferenceId("0001").totalVenta(2500d).pedido(pedido).build();
//
//			pedido.setFactura(factura);
//
//			pedidoRepository.save(pedido);

		};
	}
}