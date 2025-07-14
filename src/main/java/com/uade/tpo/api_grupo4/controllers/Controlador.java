package com.uade.tpo.api_grupo4.controllers;

import com.uade.tpo.api_grupo4.controllers.courseAttend.AsistenciaDTO;
import com.uade.tpo.api_grupo4.controllers.courseAttend.AsistenciaResultadoDTO;
import com.uade.tpo.api_grupo4.controllers.courseSchedule.CourseScheduleView;
import com.uade.tpo.api_grupo4.controllers.courseSchedule.CreateCourseScheduleRequest;
import com.uade.tpo.api_grupo4.controllers.courses.CancelacionDTO;
import com.uade.tpo.api_grupo4.controllers.courses.CourseView;
import com.uade.tpo.api_grupo4.controllers.courses.InscripcionDTO;
import com.uade.tpo.api_grupo4.controllers.courses.InscripcionView;
import com.uade.tpo.api_grupo4.controllers.courses.MetodoDePago;
import com.uade.tpo.api_grupo4.controllers.courses.ResultadoCancelacionDTO;
import com.uade.tpo.api_grupo4.controllers.courses.TipoReintegro;
import com.uade.tpo.api_grupo4.controllers.courses.InscripcionExitosaDTO;
import com.uade.tpo.api_grupo4.controllers.person.AuthenticationResponse;
import com.uade.tpo.api_grupo4.controllers.person.LoginRequest;
import com.uade.tpo.api_grupo4.controllers.person.RegisterRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateRecipeRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateReviewRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateTypeRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.CreateUnitRequest;
import com.uade.tpo.api_grupo4.controllers.recipe.MaterialRequestDTO;
import com.uade.tpo.api_grupo4.controllers.recipe.StepRequestDTO;
import com.uade.tpo.api_grupo4.controllers.student.PaymentHistoryDTO;
import com.uade.tpo.api_grupo4.controllers.student.StudentDataDTO;
import com.uade.tpo.api_grupo4.controllers.user.BecomeStudentRequest;
import com.uade.tpo.api_grupo4.entity.Course;
import com.uade.tpo.api_grupo4.entity.CourseAttended;
import com.uade.tpo.api_grupo4.entity.CourseMode;
import com.uade.tpo.api_grupo4.entity.CourseSchedule;
import com.uade.tpo.api_grupo4.entity.Headquarter;
import com.uade.tpo.api_grupo4.entity.Ingredient;
import com.uade.tpo.api_grupo4.entity.Inscripcion;
import com.uade.tpo.api_grupo4.entity.MaterialUsed;
import com.uade.tpo.api_grupo4.entity.PendingUser;
import com.uade.tpo.api_grupo4.entity.Person;
import com.uade.tpo.api_grupo4.entity.Recipe;
import com.uade.tpo.api_grupo4.entity.Review;
import com.uade.tpo.api_grupo4.entity.SavedRecipe;
import com.uade.tpo.api_grupo4.entity.Step;
import com.uade.tpo.api_grupo4.entity.Student;
import com.uade.tpo.api_grupo4.entity.TypeOfRecipe;
import com.uade.tpo.api_grupo4.entity.Unit;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.CourseException;
import com.uade.tpo.api_grupo4.exceptions.CourseScheduleException;
import com.uade.tpo.api_grupo4.exceptions.HeadquarterException;
import com.uade.tpo.api_grupo4.exceptions.StudentException;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.repository.CourseAttendRepository;
import com.uade.tpo.api_grupo4.repository.CourseRepository;
import com.uade.tpo.api_grupo4.repository.CourseScheduleRepository;
import com.uade.tpo.api_grupo4.repository.HeadquarterRepository;
import com.uade.tpo.api_grupo4.repository.InscripcionRepository;
import com.uade.tpo.api_grupo4.repository.IngredientRepository;
import com.uade.tpo.api_grupo4.repository.MaterialUsedRepository;
import com.uade.tpo.api_grupo4.repository.PendingUserRepository;
import com.uade.tpo.api_grupo4.repository.PersonRepository;
import com.uade.tpo.api_grupo4.repository.RecipeRepository;
import com.uade.tpo.api_grupo4.repository.ReviewRepository;
import com.uade.tpo.api_grupo4.repository.SavedRecipeRepository;
import com.uade.tpo.api_grupo4.repository.StepRepository;
import com.uade.tpo.api_grupo4.repository.StudentRepository;
import com.uade.tpo.api_grupo4.repository.TypeOfRecipeRepository;
import com.uade.tpo.api_grupo4.repository.UnitRepository;
import com.uade.tpo.api_grupo4.repository.UserRepository;
import com.uade.tpo.api_grupo4.service.EmailService;
import com.uade.tpo.api_grupo4.service.JwtService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Controlador {

	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final CourseScheduleRepository courseSchedRepository;
	private final HeadquarterRepository headquarterRepository;
	private final CourseAttendRepository courseAttendRepository;
	private final InscripcionRepository inscripcionRepository;
	private final RecipeRepository recipeRepository;
	private final UnitRepository unitRepository;
	private final MaterialUsedRepository materialUsedRepository;
	private final IngredientRepository ingredientRepository;
	private final TypeOfRecipeRepository typeOfRecipeRepository;
	private final StepRepository stepRepository;
	private final ReviewRepository reviewRepository;
	private final SavedRecipeRepository savedRecipeRepository;
	private final JwtService jwtService;
	private final PersonRepository personRepository;
	private final AuthenticationManager authenticationManager;
	private final PendingUserRepository pendingUserRepository;
	private final EmailService emailService;

	@PersistenceContext
    private EntityManager entityManager;
	//-----------------------------------------------Metodos Publicos--------------------------------------------------------------------------------------------------------------------------------------------------------
	public boolean aliasExists(String alias) {
		return userRepository.existsByUsername(alias) || studentRepository.existsByUsername(alias);
	}

	public boolean emailExists(String email) {
		return userRepository.existsByEmail(email) || studentRepository.existsByEmail(email);
	}

	//-----------------------------------------------Registro y Login--------------------------------------------------------------------------------------------------------------------------------------------------------

	//--------Login--------
	public AuthenticationResponse loginUsuario(LoginRequest request) throws Exception {

		User user = userRepository.findByUsername(request.getUsername());

		if (user != null) {
			if (user.getPassword().equals(request.getPassword())) {
				String jwtToken = jwtService.generateToken(user);
				return AuthenticationResponse.builder().token(jwtToken).build();
			}
		} else {
			Student student = studentRepository.findByUsername(request.getUsername());

			if (student != null) {
				if (student.getPassword().equals(request.getPassword())) {
					String jwtToken = jwtService.generateToken(student);
					return AuthenticationResponse.builder().token(jwtToken).build();
				}
			}
		}

		throw new Exception("Credenciales incorrectas");
	}

	//--------Registro--------

	public void crearUsuarioGeneral(RegisterRequest request) throws UserException {
		if (userRepository.existsByUsername(request.getUsername()) || studentRepository.existsByUsername(request.getUsername())) {
			throw new UserException("El nombre de usuario '" + request.getUsername() + "' ya está en uso.");
		}
		if (userRepository.existsByEmail(request.getEmail()) || studentRepository.existsByEmail(request.getEmail())) {
			throw new UserException("El correo electrónico '" + request.getEmail() + "' ya está registrado.");
		}

		if (request.getPermissionGranted() == true) {
			User nuevoUsuario = User.builder()
					.username(request.getUsername())
					.email(request.getEmail())
					.password(request.getPassword())
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.phone(request.getPhone())
					.address(request.getAddress())
					.urlAvatar(request.getUrlAvatar())
					.permissionGranted(true)
					.recipes(new ArrayList<>())
					.savedRecipes(new ArrayList<>())
					.reviews(new ArrayList<>())
					.build();

			userRepository.save(nuevoUsuario);
			System.out.println("Usuario agregado con éxito: " + nuevoUsuario.getUsername());

		} else {
			Student nuevoEstudiante = Student.builder()
					.username(request.getUsername())
					.email(request.getEmail())
					.password(request.getPassword())
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.phone(request.getPhone())
					.address(request.getAddress())
					.urlAvatar(request.getUrlAvatar())
					.permissionGranted(false)
					.cardNumber(request.getCardNumber())
					.dniFrente(request.getDniFrente())
					.dniDorso(request.getDniDorso())
					.nroTramite(request.getNroTramite())
					.cuentaCorriente(0)
					.nroDocumento(request.getNroDocumento())
					.tipoTarjeta(request.getTipoTarjeta())
					.build();

			studentRepository.save(nuevoEstudiante);
			System.out.println("Estudiante agregado con éxito: " + nuevoEstudiante.getUsername());
		}
	}

//--------Iniciar Registro--------
	public void iniciarRegistro(RegisterRequest request) throws UserException {
		if (aliasExists(request.getUsername())) {
			throw new UserException("El nombre de usuario '" + request.getUsername() + "' ya está en uso.");
		}
		if (emailExists(request.getEmail())) {
			throw new UserException("El correo electrónico '" + request.getEmail() + "' ya está registrado.");
		}

		String code = String.format("%04d", new Random().nextInt(10000));

		PendingUser pendingUser = PendingUser.builder()
				.username(request.getUsername()).email(request.getEmail()).password(request.getPassword())
				.firstName(request.getFirstName()).lastName(request.getLastName()).phone(request.getPhone())
				.address(request.getAddress()).urlAvatar(request.getUrlAvatar())
				.permissionGranted(request.getPermissionGranted())
				.cardNumber(request.getCardNumber()).nroTramite(request.getNroTramite())
				.nroDocumento(request.getNroDocumento()).tipoTarjeta(request.getTipoTarjeta())
				.dniFrente(request.getDniFrente()).dniDorso(request.getDniDorso())
				.verificationCode(code)
				.expiryDate(LocalDateTime.now().plusMinutes(15))
				.build();

		pendingUserRepository.save(pendingUser);

		emailService.sendVerificationCode(pendingUser.getEmail(), code);
	}

	//--------Finalizar Registro--------
	public void finalizarRegistro(String email, String code) throws UserException {
		PendingUser pendingUser = pendingUserRepository.findById(email)
				.orElseThrow(() -> new UserException("No se encontró un registro pendiente para este email. Puede que haya expirado."));

		if (pendingUser.getExpiryDate().isBefore(LocalDateTime.now())) {
			pendingUserRepository.delete(pendingUser);
			throw new UserException("El código de verificación ha expirado. Por favor, intenta registrarte de nuevo.");
		}

		if (!pendingUser.getVerificationCode().equals(code)) {
			throw new UserException("El código de verificación es incorrecto.");
		}

		RegisterRequest finalRequest = new RegisterRequest();
		finalRequest.setUsername(pendingUser.getUsername());
		finalRequest.setEmail(pendingUser.getEmail());
		finalRequest.setPassword(pendingUser.getPassword());
		finalRequest.setFirstName(pendingUser.getFirstName());
		finalRequest.setLastName(pendingUser.getLastName());
		finalRequest.setPhone(pendingUser.getPhone());
		finalRequest.setAddress(pendingUser.getAddress());
		finalRequest.setUrlAvatar(pendingUser.getUrlAvatar());
		finalRequest.setPermissionGranted(pendingUser.getPermissionGranted());
		finalRequest.setCardNumber(pendingUser.getCardNumber());
		finalRequest.setDniFrente(pendingUser.getDniFrente());
		finalRequest.setDniDorso(pendingUser.getDniDorso());
		finalRequest.setNroTramite(pendingUser.getNroTramite());
		finalRequest.setNroDocumento(pendingUser.getNroDocumento());
		finalRequest.setTipoTarjeta(pendingUser.getTipoTarjeta());

		crearUsuarioGeneral(finalRequest);

		pendingUserRepository.delete(pendingUser);
	}

	//-----------------------------------------------Students--------------------------------------------------------------------------------------------------------------------------------------------------------
	public List<Student> todosLosEstudiantes() throws StudentException {
		List<Student> students = studentRepository.findAll();
		if (students.isEmpty()) {
			throw new StudentException("No se encontraron estudiantes en la base de datos.");
		}
		return students;
	}
	public Student agregarEstudiante(Long id, Student student) throws StudentException {
		return studentRepository.findById(id)
				.map(existingStudent -> {
					existingStudent.setCardNumber(student.getCardNumber());
					existingStudent.setDniFrente(student.getDniFrente());
					existingStudent.setDniDorso(student.getDniDorso());
					existingStudent.setNroTramite(student.getNroTramite());
					existingStudent.setCuentaCorriente(student.getCuentaCorriente());
					existingStudent.setNroDocumento(student.getNroDocumento());
					existingStudent.setTipoTarjeta(student.getTipoTarjeta());
					return studentRepository.save(existingStudent);
				})
				.orElseThrow(()-> new StudentException("No existe el estudiante con el id" + id));
	}

	public void eliminarEstudiante(Long studentId) throws StudentException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentException("El estudiante con id " + studentId + " no existe."));
		studentRepository.delete(student);
		System.out.println("Estudiante eliminado: " + studentId);
	}

	public void modificarPasswordStudent(Long studentId, String newPassword) throws StudentException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentException("El estudiante con id " + studentId + " no existe."));
		student.setPassword(newPassword);
		studentRepository.save(student);
		System.out.println("Contraseña actualizada para el estudiante: " + studentId);
	}

	public Student findStudentByUsername(String username) {
		return studentRepository.findByUsername(username);
	}

	public Student findStudentByEmail(String email){
		return studentRepository.findByEmail(email);
	}

	public boolean loginEstudiante(LoginRequest loginRequest) throws Exception {
		Student student = findStudentByEmail(loginRequest.getUsername());
		if (student == null) {
			return false;
		}
		return student.getPassword().equals(loginRequest.getPassword());
	}

	public StudentDataDTO getStudentData(Long studentId) {
		Student student = studentRepository.findById(studentId)
			.orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

		// Aquí podrías usar un Mapper o construir el DTO manualmente
		StudentDataDTO dto = new StudentDataDTO();
		dto.setId(student.getId());
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setCuentaCorriente(student.getCuentaCorriente());

		return dto;
	}

	public List<PaymentHistoryDTO> getPaymentHistory(Long studentId) {
		List<Inscripcion> inscripciones = inscripcionRepository.findByStudentIdAndEstado(studentId, "ACTIVA");

		return inscripciones.stream().map(inscripcion -> {
			PaymentHistoryDTO dto = new PaymentHistoryDTO();
			dto.setId(inscripcion.getId());
			dto.setDate(inscripcion.getFechaInscripcion());
			dto.setReceipt(String.format("%05d", inscripcion.getId())); // Nro de recibo simple
			dto.setCourseName(inscripcion.getCourseSchedule().getCourse().getName());
			dto.setAmount(inscripcion.getCourseSchedule().getCourse().getPrice());
			return dto;
		}).collect(Collectors.toList());
	}



	//-----------------------------------------------Users--------------------------------------------------------------------------------------------------------------------------------------------------------
	public List<User> todosLosUsuarios() throws UserException {
		List<User> usuarios = userRepository.findAll();
		if (usuarios.isEmpty()) {
			throw new UserException("No se encontraron usuarios en la base de datos.");
		}
		return usuarios;
	}

	public void eliminarUsuario(Long userId) throws UserException {
		User usuario = userRepository.findById(userId).orElseThrow(() -> new UserException("El usuario con id " + userId + " no existe."));
		userRepository.delete(usuario);
		System.out.println("Usuario eliminado: " + userId);
	}

	@Transactional
    public Student upgradeUserToStudent(Person person, BecomeStudentRequest request) throws Exception {
        // Verificamos que la persona que hace la petición sea realmente un User
        if (!(person instanceof User)) {
            throw new Exception("La operación no es válida: la cuenta ya es de un estudiante.");
        }
        
        Long personId = person.getId();

        // 1. Eliminamos la fila de la tabla 'user' usando una consulta SQL nativa.
        entityManager.createNativeQuery("DELETE FROM user WHERE id = :personId")
                .setParameter("personId", personId)
                .executeUpdate();

        // 2. Insertamos la nueva fila en la tabla 'student' con los datos correspondientes.
        entityManager.createNativeQuery("INSERT INTO student (id, card_number, dni_frente, dni_dorso, nro_tramite, cuenta_corriente, nro_documento, tipo_tarjeta) VALUES (:id, :cardNumber, :dniFrente, :dniDorso, :nroTramite, :cuentaCorriente, :nroDocumento, :tipoTarjeta)")
                .setParameter("id", personId)
                .setParameter("cardNumber", request.getCardNumber())
                .setParameter("dniFrente", request.getDniFrente())
                .setParameter("dniDorso", request.getDniDorso())
                .setParameter("nroTramite", request.getNroTramite())
                .setParameter("cuentaCorriente", 0)
                .setParameter("nroDocumento", request.getNroDocumento())
                .setParameter("tipoTarjeta", request.getTipoTarjeta())
                .executeUpdate();
        
        // ▼▼▼ ¡CAMBIO CLAVE! ▼▼▼
        // 3. Actualizamos el flag 'permissionGranted' directamente en la tabla 'person'.
        entityManager.createNativeQuery("UPDATE person SET permission_granted = false WHERE id = :personId")
                .setParameter("personId", personId)
                .executeUpdate();

        // 4. Forzamos la ejecución de las consultas y limpiamos la caché de la sesión.
        entityManager.flush();
        entityManager.clear();
        
        // 5. Devolvemos el objeto Student recién constituido, buscándolo de nuevo para asegurar que refleje el estado final.
        return studentRepository.findById(personId)
                .orElseThrow(() -> new Exception("Error crítico al reconvertir el usuario a estudiante."));
    }

	public void modificarPasswordUsuario(Long userId, String newPassword) throws UserException {
		User usuario = userRepository.findById(userId).orElseThrow(() -> new UserException("El usuario con id " + userId + " no existe."));
		usuario.setPassword(newPassword);
		userRepository.save(usuario);
		System.out.println("Contraseña actualizada para el usuario: " + userId);
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	//-----------------------------------------------Recetas--------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//--------Ingredientes--------
    public Ingredient createIngredient(Ingredient ingredientData) {
        String normalizedName = ingredientData.getName().trim().toLowerCase();

        Optional<Ingredient> existingIngredient = ingredientRepository.findByName(normalizedName);

        if (existingIngredient.isPresent()) {
            throw new IllegalStateException("El ingrediente '" + normalizedName + "' ya existe.");
        } else {
            Ingredient newIngredient = new Ingredient();
            newIngredient.setName(normalizedName);
            return ingredientRepository.save(newIngredient);
        }
    }

	//--------Materiales usados--------

	public MaterialUsed addMaterialToRecipe(Long recipeId, MaterialRequestDTO materialRequest) throws Exception {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new Exception("No se encontró la receta con ID: " + recipeId));

        Ingredient ingredient = ingredientRepository.findById(materialRequest.getIngredientId())
                .orElseThrow(() -> new Exception("No se encontró el ingrediente con ID: " + materialRequest.getIngredientId()));

        Unit unit = null;
        if (materialRequest.getUnitId() != null) {
            unit = unitRepository.findById(materialRequest.getUnitId())
                    .orElseThrow(() -> new Exception("No se encontró la unidad con ID: " + materialRequest.getUnitId()));
        }

        MaterialUsed newMaterial = MaterialUsed.builder()
                .recipe(recipe)
                .ingredient(ingredient)
                .quantity(materialRequest.getQuantity())
                .unity(unit)
                .observation(materialRequest.getObservation())
                .build();
        
        return materialUsedRepository.save(newMaterial);
    }

	//--------Crear Tipo de receta--------
	public TypeOfRecipe createTypeOfRecipe(CreateTypeRequest request) throws Exception {
		String typeName = request.getName().trim();

		if (typeOfRecipeRepository.findByNameIgnoreCase(typeName).isPresent()) {
			throw new Exception("El tipo de receta '" + typeName + "' ya existe.");
		}

		TypeOfRecipe newType = TypeOfRecipe.builder()
				.name(typeName)
				.build();

		return typeOfRecipeRepository.save(newType);
	}


	//--------Crear Tipo de unidad--------

	public Unit createUnit(CreateUnitRequest request) throws Exception {
		String unitDescription = request.getDescription().trim();

		if (unitRepository.findByDescriptionIgnoreCase(unitDescription).isPresent()) {
			throw new Exception("La unidad '" + unitDescription + "' ya existe.");
		}

		Unit newUnit = Unit.builder()
				.description(unitDescription)
				.build();

		return unitRepository.save(newUnit);
	}

	//--------Crear receta--------

	@Transactional
	public Recipe createRecipeWithMaterials(CreateRecipeRequest request, Person author) throws Exception {
		
		TypeOfRecipe typeOfRecipe = typeOfRecipeRepository.findById(request.getTypeOfRecipeId())
				.orElseThrow(() -> new Exception("Tipo de receta no encontrado con ID: " + request.getTypeOfRecipeId()));

		Recipe newRecipe = Recipe.builder()
				.recipeName(request.getRecipeName())
				.user(author)
				.mainPicture(request.getMainPicture())
				.servings(request.getServings())
				.comensales(request.getCantidadPersonas())
				.typeOfRecipe(typeOfRecipe)
				.ingredients(new ArrayList<>())
				.description(new ArrayList<>())
				.reviews(new ArrayList<>())
				.descriptionGeneral(request.getDescriptionGeneral())
				.build();

		if (request.getIngredients() != null) {
			for (MaterialRequestDTO materialDto : request.getIngredients()) {
				
				Ingredient ingredientToUse;

				if (materialDto.getIngredientId() != null) {
					ingredientToUse = ingredientRepository.findById(materialDto.getIngredientId())
							.orElseThrow(() -> new Exception("Ingrediente no encontrado con el ID proporcionado: " + materialDto.getIngredientId()));
				
				} else if (materialDto.getIngredientName() != null && !materialDto.getIngredientName().trim().isEmpty()) {
					String ingredientName = materialDto.getIngredientName().trim().toLowerCase();
					
					ingredientToUse = ingredientRepository.findByName(ingredientName)
							.orElseGet(() -> {
								Ingredient newIngredient = Ingredient.builder().name(ingredientName).build();
								return ingredientRepository.save(newIngredient);
							});

				} else {
					throw new Exception("Se debe proporcionar 'ingredientId' o 'ingredientName' para cada material.");
				}

				Unit unit = materialDto.getUnitId() != null ? unitRepository.findById(materialDto.getUnitId())
						.orElseThrow(() -> new Exception("Unidad no encontrada con ID: " + materialDto.getUnitId())) : null;

				MaterialUsed material = MaterialUsed.builder()
						.recipe(newRecipe)
						.ingredient(ingredientToUse)
						.unity(unit)
						.quantity(materialDto.getQuantity())
						.observation(materialDto.getObservation())
						.build();
				newRecipe.getIngredients().add(material);
			}
		}
		
		if (request.getSteps() != null) {
			for (StepRequestDTO stepDto : request.getSteps()) {
				Step step = Step.builder()
						.recipe(newRecipe)
						.numberOfStep(stepDto.getNumberOfStep())
						.comment(stepDto.getComment())
						.imagenPaso(stepDto.getImagenPaso())
						.videoPaso(stepDto.getVideoPaso())
						.build();
				newRecipe.getDescription().add(step);
			}
		}

		return recipeRepository.save(newRecipe);
	}
		
	//--------Review--------
	public Review createReview(Long recipeId, CreateReviewRequest request, Person author) throws Exception {
		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new Exception("No se encontró la receta con ID: " + recipeId));

		Review newReview = Review.builder()
				.recipe(recipe)
				.user(author)
				.rating(request.getRating())
				.comment(request.getComment())
				.build();

		return reviewRepository.save(newReview);
	}

		//--------MODIFICAR RECETA-----------
	@Transactional
	public Recipe updateRecipe(Long recipeId, CreateRecipeRequest request, Person author) throws Exception {
		Recipe existingRecipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new Exception("Receta no encontrada con ID: " + recipeId));

		// Verificar que el autor de la receta sea la misma persona que intenta modificarla
		if (!existingRecipe.getUser().getId().equals(author.getId())) {
			throw new Exception("No tienes permiso para modificar esta receta.");
		}

		// Actualizar campos principales de la receta
		existingRecipe.setRecipeName(request.getRecipeName());
		existingRecipe.setMainPicture(request.getMainPicture());
		existingRecipe.setServings(request.getServings());
		existingRecipe.setComensales(request.getCantidadPersonas());
		existingRecipe.setDescriptionGeneral(request.getDescriptionGeneral());

		// Actualizar el tipo de receta si se proporciona
		if (request.getTypeOfRecipeId() != null) {
			TypeOfRecipe typeOfRecipe = typeOfRecipeRepository.findById(request.getTypeOfRecipeId())
					.orElseThrow(() -> new Exception("Tipo de receta no encontrado con ID: " + request.getTypeOfRecipeId()));
			existingRecipe.setTypeOfRecipe(typeOfRecipe);
		}

		// --- Actualizar Ingredientes (MaterialUsed) ---
		// Borrar los materiales existentes para esta receta
		materialUsedRepository.deleteAll(existingRecipe.getIngredients());
		existingRecipe.getIngredients().clear(); // Limpiar la lista en memoria

		if (request.getIngredients() != null) {
			for (MaterialRequestDTO materialDto : request.getIngredients()) {
				Ingredient ingredientToUse;

				if (materialDto.getIngredientId() != null) {
					ingredientToUse = ingredientRepository.findById(materialDto.getIngredientId())
							.orElseThrow(() -> new Exception("Ingrediente no encontrado con el ID proporcionado: " + materialDto.getIngredientId()));
				} else if (materialDto.getIngredientName() != null && !materialDto.getIngredientName().trim().isEmpty()) {
					String ingredientName = materialDto.getIngredientName().trim().toLowerCase();
					ingredientToUse = ingredientRepository.findByName(ingredientName)
							.orElseGet(() -> {
								Ingredient newIngredient = Ingredient.builder().name(ingredientName).build();
								return ingredientRepository.save(newIngredient);
							});
				} else {
					throw new Exception("Se debe proporcionar 'ingredientId' o 'ingredientName' para cada material.");
				}

				Unit unit = materialDto.getUnitId() != null ? unitRepository.findById(materialDto.getUnitId())
						.orElseThrow(() -> new Exception("Unidad no encontrada con ID: " + materialDto.getUnitId())) : null;

				MaterialUsed material = MaterialUsed.builder()
						.recipe(existingRecipe)
						.ingredient(ingredientToUse)
						.unity(unit)
						.quantity(materialDto.getQuantity())
						.observation(materialDto.getObservation())
						.build();
				existingRecipe.getIngredients().add(material);
			}
		}

		// --- Actualizar Pasos (Step) ---
		// Borrar los pasos existentes para esta receta
		stepRepository.deleteAll(existingRecipe.getDescription());
		existingRecipe.getDescription().clear(); // Limpiar la lista en memoria

		if (request.getSteps() != null) {
			for (StepRequestDTO stepDto : request.getSteps()) {
				Step step = Step.builder()
						.recipe(existingRecipe)
						.numberOfStep(stepDto.getNumberOfStep())
						.comment(stepDto.getComment())
						.imagenPaso(stepDto.getImagenPaso())
						.videoPaso(stepDto.getVideoPaso())
						.build();
				existingRecipe.getDescription().add(step);
			}
		}

		return recipeRepository.save(existingRecipe);
	}
	
	@Transactional
    public boolean toggleSaveRecipe(Long userId, Long recipeId) {
        // Asegúrate de que PersonRepository esté inyectado en Controlador
        Person user = personRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + recipeId));

        Optional<SavedRecipe> existingSavedRecipe = savedRecipeRepository.findByUserAndRecipe(user, recipe);

        if (existingSavedRecipe.isPresent()) {
            savedRecipeRepository.delete(existingSavedRecipe.get());
            return false; // Indicamos que se desguardó
        } else {
            SavedRecipe newSavedRecipe = SavedRecipe.builder()
                    .user(user)
                    .recipe(recipe)
                    .build();
            savedRecipeRepository.save(newSavedRecipe);
            return true; // Indicamos que se guardó
        }
    }

    public List<Recipe> getSavedRecipesByUser(Long userId) {
        // Asegúrate de que PersonRepository esté inyectado en Controlador
        Person user = personRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        List<SavedRecipe> savedRecipes = savedRecipeRepository.findByUser(user);
        // Mapeamos SavedRecipe a Recipe para devolver la lista de recetas
        return savedRecipes.stream()
                .map(SavedRecipe::getRecipe)
                .collect(Collectors.toList());
    }

    public boolean isRecipeSavedByUser(Long userId, Long recipeId) {
        // Asegúrate de que PersonRepository esté inyectado en Controlador
        Person user = personRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + recipeId));
        return savedRecipeRepository.existsByUserAndRecipe(user, recipe);
    }


	//-----------------------------------------------Busquedas--------------------------------------------------------------------------------------------------------------------------------------------------------

	//--------Receta por id--------
	public Recipe getRecipeById(Long id) { // O como hayas llamado al método
		// ✅ USA EL NUEVO MÉTODO DEL REPOSITORIO
		return recipeRepository.findByIdWithReviewsAndAuthor(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receta no encontrada"));
	}

	//--------Todas las recetas--------
	public Page<Recipe> findAllRecipes(String sortOrder, int page, int size) {
		Sort sort;
		switch (sortOrder.toLowerCase()) {
			case "newest":
				sort = Sort.by("id").descending();
				break;
			case "oldest":
				sort = Sort.by("id").ascending();
				break;
			case "alpha_asc":
			default:
				sort = Sort.by("recipeName").ascending();
				break;
		}

		Pageable pageable = PageRequest.of(page, size, sort);

		return recipeRepository.findAll(pageable);
	}

	private Pageable createPageable(String sortOrder, int page, int size) {
        Sort sort;
        switch (sortOrder.toLowerCase()) {
            case "newest":
                sort = Sort.by("id").descending();
                break;
            case "oldest":
                sort = Sort.by("id").ascending();
                break;
            case "alpha_asc":
            default:
                sort = Sort.by("recipeName").ascending();
                break;
        }
        return PageRequest.of(page, size, sort);
    }

	//--------Todas las unidades--------
	public List<Unit> findAllUnits() {
        return unitRepository.findAll(Sort.by("description").ascending());
    }

	//--------Recetas por nombre--------
	public Page<Recipe> findRecipesByName(String name, String sortOrder, int page, int size) {
        Pageable pageable = createPageable(sortOrder, page, size);
        return recipeRepository.findByRecipeNameContainingIgnoreCase(name, pageable);
    }

	//--------Recetas por nombre de usuario--------
	public Page<Recipe> findRecipesByAuthor(String authorUsername, String sortOrder, int page, int size) {
        Pageable pageable = createPageable(sortOrder, page, size);
        return recipeRepository.findByUserUsernameContainingIgnoreCase(authorUsername, pageable);
    }

	//--------Recetas por tipo--------
	public Page<Recipe> findRecipesByType(Long typeId, String sortOrder, int page, int size) {
        Pageable pageable = createPageable(sortOrder, page, size);
        
        return recipeRepository.findByTypeOfRecipeId(typeId, pageable);
    }

	//--------Todas los tipos de recetas--------
	public List<TypeOfRecipe> findAllTypes() {
		return typeOfRecipeRepository.findAll(Sort.by("name").ascending());
	}

	//--------Recetas por ingrediente o NO ingrediente--------
	public Page<Recipe> findRecipesByIngredientPresence(String ingredientName, boolean contains, String sortOrder, int page, int size) {
		Pageable pageable = createPageable(sortOrder, page, size);
		
		if (contains) {
			return recipeRepository.findRecipesWithIngredient(ingredientName, pageable);
		} else {
			return recipeRepository.findRecipesWithoutIngredient(ingredientName, pageable);
		}
	}


	//-----------------------------------------------Headquarters--------------------------------------------------------------------------------------------------------------------------------------------------------

	public List<Headquarter> todosLasSedes() throws HeadquarterException {
			List<Headquarter> sedes = headquarterRepository.findAll();
			if (sedes.isEmpty()) {
				throw new HeadquarterException("No se encontraron sedes en la base de datos.");
			}
			return sedes;
	}

	public void inicializarSedes() throws Exception {
		try{	
            Headquarter headquarter1 = new Headquarter(null, "Caballito","45678889003", "Rosario 789", "sedecaballitotl@gmail.com", "+5491130561833", "20% de reintegro", 0.2, "-70% descuento", 0.7);
            Headquarter headquarter2 = new Headquarter(null, "Devoto", "43445567880", "Chivilcoy 3700", "sededevototl@gmail.com", "+5491120443789", "30% de reintegro", 0.3, "-70% descuento", 0.7);
            Headquarter headquarter3 = new Headquarter(null, "Retiro","44293778034", "Pelegrini 1500", "sederetirotl@gmail.com", "+5491129387029", "25% de reintegro", 0.25, "-60% descuento", 0.6);

            headquarterRepository.save(headquarter1); 
			headquarterRepository.save(headquarter2);
			headquarterRepository.save(headquarter3);

		 } catch (HeadquarterException error) {

        	throw new HeadquarterException(error.getMessage());
      } catch (Exception error) {
				throw new Exception("[Controlador.inicializarCursos] -> " + error.getMessage());
			}
    	}

	
	public Headquarter getHeadquarterByName(String name) throws Exception {
		try{
			return headquarterRepository.findByName(name).orElseThrow(() -> new HeadquarterException("Sede no encontrada"));
		} catch (HeadquarterException error) {
			throw new HeadquarterException(error.getMessage());
		} catch (Exception error) {
			throw new Exception("[Controlador.getCourseByName] -> " + error.getMessage());
		}
	}

      
    public Headquarter updateSede(Headquarter headquarter) throws Exception {
          try {
            if (!headquarterRepository.existsById(headquarter.getId())) 
              throw new HeadquarterException("la sede con id: '" + headquarter.getId() + "' no existe.");
            
            Headquarter updatedSede = headquarterRepository.save(headquarter);
            return updatedSede;
          } catch (HeadquarterException error) {
            throw new HeadquarterException(error.getMessage());
          } catch (Exception error) {
            throw new Exception("[Controlador.updateSede] -> " + error.getMessage());
          }
    }

	public Headquarter saveSede(Headquarter headquarter) throws Exception {
		try{
        Headquarter sede = headquarterRepository.save(headquarter);   
        return sede;

		} catch (Exception error) {
            throw new Exception("[Controlador.saveSede] -> " + error.getMessage());
          }
        }
    

	@Transactional
    public void deleteSede(Long id) throws Exception {
          try {
              headquarterRepository.deleteById(id);
          } catch (Exception error) {
            throw new Exception("[Controlador.deleteSede] -> " + error.getMessage());
          }
    }

	public void cargarSede(Long sedeId, Long courseId){
		Headquarter sedeSeleccionada = headquarterRepository.findById(sedeId).orElseThrow(() -> new HeadquarterException("La sede con id " + sedeId + " no existe."));
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseException("Curso no encontrado"));
		
		List<Headquarter> sedes = new ArrayList<>();
		List<Headquarter> existeListaSedes = course.getSedes();
		if( existeListaSedes == null){
			sedes.add(sedeSeleccionada);
			course.setSedes(sedes);
		} else existeListaSedes.add(sedeSeleccionada);
		courseRepository.save(course);
		
		courseRepository.save(course);
	}
	
	//-----------------------------------------------Courses--------------------------------------------------------------------------------------------------------------------------------------------------------

	public List<Course> todosLosCursos() throws CourseException {
			List<Course> cursos = courseRepository.findAll();
			if (cursos.isEmpty()) {
				throw new CourseException("No se encontraron cursos en la base de datos.");
			}
			return cursos;
	}
	

	public Course getCourseById(Long courseId) throws Exception {
		try{
			return courseRepository.findById(courseId).orElseThrow(() -> new CourseException("Curso no encontrado"));
		} catch (CourseException error) {
			throw new CourseException(error.getMessage());
		} catch (Exception error) {
			throw new Exception("[Controlador.getCourseByName] -> " + error.getMessage());
		}
	}

	public CourseView getCourseViewById(Long courseId) throws Exception {
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new CourseException("Curso no encontrado con ID: " + courseId));
		return course.toView(); // Devolvemos la vista en lugar de la entidad
	}

	public Course createCourse(Course course) throws Exception {

          try {
			
			Course createdCourse = courseRepository.save(course);
            return createdCourse;
          } catch (Exception error) {
            throw new Exception("[Controlador.createCourse] -> " + error.getMessage());
          }
        }

	public void inicializarCursos() throws Exception {
		try {

			Course course1 = new Course(null, "Cocina Vegana", "Familiarizate con la cocina vegana.", "No necesitas conocimientos previos.", "Aprenderás las bases fundamentales para sustituir ingredientes de origen animal sin sacrificar el sabor ni la calidad nutricional.", 120, 600, CourseMode.MIXTO,
					LocalDate.parse("2025-08-08"), // <-- CAMBIO AQUÍ
					LocalDate.parse("2025-11-08"), // <-- CAMBIO AQUÍ
					new ArrayList<>(),
                	new ArrayList<>()
			);

			Course cours2 = new Course(null, "Cocina Asiática", "Comprendé técnicas básicas clave.", "Conocimientos básicos de cocina.", "Domina el arte del wok, aprende a armar el sushi perfecto y descubre el equilibrio de sabores que hace única a la gastronomía asiática.", 180, 800, CourseMode.PRESENCIAL,
					LocalDate.parse("2025-07-18"), // <-- CAMBIO AQUÍ
					LocalDate.parse("2025-08-18"), // <-- CAMBIO AQUÍ
					new ArrayList<>(),
                	new ArrayList<>()
			);

			Course course3 = new Course(null, "Reposteria Cacera", "Aprendé a conocer sobre decoracion.", "Material de reposteria.", "Nuestro nuevo curso de repostería casera te enseña a preparar tortas, galletitas y budines como los de antes. Recetas fáciles y riquísimas.", 120, 400, CourseMode.VIRTUAL,
					LocalDate.parse("2025-07-11"), // <-- CAMBIO AQUÍ
					LocalDate.parse("2025-08-11"), // <-- CAMBIO AQUÍ
					new ArrayList<>(),
                	new ArrayList<>()
			);

			courseRepository.save(course1);
			courseRepository.save(cours2);
			courseRepository.save(course3);

		} catch (CourseException error) {
			throw new CourseException(error.getMessage());
		} catch (Exception error) {
			throw new Exception("[Controlador.inicializarCursos] -> " + error.getMessage());
		}
	}

	public Course updateCourse(Course course) throws Exception {
          try {
            if (!courseRepository.existsById(course.getId())) 
              throw new CourseException("El curso con id: '" + course.getId() + "' no existe.");
            
            Course updatedCourse = courseRepository.save(course);
            return updatedCourse;
          } catch (CourseException error) {
            throw new CourseException(error.getMessage());
          } catch (Exception error) {
            throw new Exception("[Controlador.updateCourse] -> " + error.getMessage());
          }
    }

	@Transactional
    public void deleteCourse(Long id) throws Exception {
          try {
              courseRepository.deleteById(id);
          } catch (Exception error) {
            throw new Exception("[Controlador.deleteCourse] -> " + error.getMessage());
          }
    }
    
    public List<CourseView> findByMode(CourseMode mode) {
        return courseRepository.findByMode(mode)
                .stream()
                .map(Course::toView)
                .collect(Collectors.toList());
    }
    
    public List<CourseView> findCoursesByName(String name) {
		return courseRepository.findByNameContainingIgnoreCase(name)
				.stream()
				.map(Course::toView) // Usamos el método que ya tenías para convertir
				.collect(Collectors.toList());
	}

	//-----------------------------------------------CourseSchedule--------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public CourseSchedule saveCronograma(long courseId, Long sedeId, CreateCourseScheduleRequest request) throws Exception {
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new CourseException("Curso no encontrado con ID: " + courseId));

		Headquarter sede = headquarterRepository.findById(sedeId)
			.orElseThrow(() -> new HeadquarterException("Sede no encontrada con ID: " + sedeId));

		if (!course.getSedes().contains(sede)) {
			throw new Exception("La sede con ID " + sedeId + " no está asignada a este curso.");
		}

		CourseSchedule newSchedule = CourseSchedule.builder()
				.course(course)
				.sede(sede)
				.horaInicio(request.getHoraInicio())
				.horaFin(request.getHoraFin())
				.instructor(request.getInstructor())
				.vacancy(request.getVacancy())
				.diaEnQueSeDicta(request.getDiaEnQueSeDicta())
				.build();

		return courseSchedRepository.save(newSchedule);
	}

    public CourseSchedule updateCronograma(CourseSchedule courseSchedule) throws Exception {
          try {
            if (!courseSchedRepository.existsById(courseSchedule.getId())) 
              throw new CourseScheduleException("El cronograma con id: '" + courseSchedule.getId() + "' no existe.");
            
            CourseSchedule updatedCourseSched = courseSchedRepository.save(courseSchedule);
            return updatedCourseSched;
          } catch (CourseScheduleException error) {
            throw new CourseScheduleException(error.getMessage());
          } catch (Exception error) {
            throw new Exception("[Controlador.updateCourseSchedule] -> " + error.getMessage());
          }
    }
    

	@Transactional
    public void deleteCourseSchedule(Long id) throws Exception {
          try {
              
			  courseSchedRepository.findById(id).orElseThrow(() -> new CourseScheduleException("El cronograma con id " + id + " no existe."));
			  courseSchedRepository.deleteById(id);
			
          } catch (Exception error) {
            throw new Exception("[Controlador.deleteCourseSchedule] -> " + error.getMessage());
          }
        }

	public List<CourseScheduleView> findSchedByCourse(Long courseId) {
        return courseSchedRepository.findByCourseId(courseId)
                .stream()
                .map(CourseSchedule::toView)
                .collect(Collectors.toList());
    	}

	
//----------------------------------Inscripciones----------------------------------------------------------//
	@Transactional
	public InscripcionExitosaDTO enrollStudent(InscripcionDTO inscripcionDTO) {
		// 1. BÚSQUEDA DE ENTIDADES
		Student student = studentRepository.findById(inscripcionDTO.getIdAlumno())
				.orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con ID: " + inscripcionDTO.getIdAlumno()));

		CourseSchedule courseSchedule = courseSchedRepository.findById(inscripcionDTO.getIdCronograma())
				.orElseThrow(() -> new IllegalArgumentException("Cronograma no encontrado con ID: " + inscripcionDTO.getIdCronograma()));
		
		Course course = courseSchedule.getCourse();

		// 2. VALIDACIONES DE NEGOCIO
		if (courseSchedule.getVacancy() <= 0) {
			throw new IllegalStateException("No quedan cupos disponibles para este curso.");
		}
		inscripcionRepository.findByStudentAndCourseSchedule(student, courseSchedule)
			.ifPresent(inscripcion -> {
				if ("ACTIVA".equals(inscripcion.getEstado())) {
					throw new IllegalArgumentException("El estudiante ya está inscripto y activo en este curso.");
				}
			});
		
		// 3. LÓGICA DE PROCESAMIENTO DE PAGO
		if (inscripcionDTO.getMetodoDePago() == MetodoDePago.CUENTA_CORRIENTE) {
			double precioCurso = course.getPrice();
			double saldoDisponible = student.getCuentaCorriente();

			if (saldoDisponible < precioCurso) {
				throw new IllegalStateException(String.format("Saldo insuficiente. Saldo actual: $%.2f, Costo del curso: $%.2f", saldoDisponible, precioCurso));
			}
			
			student.setCuentaCorriente(saldoDisponible - precioCurso);
			studentRepository.save(student);

		} else { // METODO_REGISTRADO
			// Se asume pago externo, no se modifica el saldo.
		}
		
		// 4. CREACIÓN DE LA INSCRIPCIÓN
		Inscripcion nuevaInscripcion = Inscripcion.builder()
				.student(student)
				.courseSchedule(courseSchedule)
				.fechaInscripcion(LocalDateTime.now())
				.estado("ACTIVA")
				.build();
		Inscripcion savedInscripcion = inscripcionRepository.save(nuevaInscripcion);

		// 5. ACTUALIZACIÓN DE VACANTES
		courseSchedule.setVacancy(courseSchedule.getVacancy() - 1);
		courseSchedRepository.save(courseSchedule);
		
		// 6. ENVÍO DE EMAIL DE CONFIRMACIÓN
		try {
			String subject = "¡Confirmación de tu inscripción al curso: " + course.getName() + "!";
			String precioFormateado = String.format("$%d", course.getPrice());

			String body = String.format(
				"Hola %s,\n\n" +
				"¡Te has inscrito exitosamente! Aquí están los detalles de tu curso:\n\n" +
				"--------------------------------------------------\n" +
				"Curso: %s\n" +
				"Descripción: %s\n" +
				"Instructor: %s\n" +
				"Duración: %d semanas\n" +
				"Modalidad: %s\n" +
				"Costo: %s\n" +
				"--------------------------------------------------\n\n" +
				"Detalles del Horario:\n" +
				"Sede: %s (%s)\n" +
				"Día: %s\n" +
				"Horario: de %s a %s hs.\n\n" +
				"¡Te esperamos!",
				student.getFirstName(),
				course.getName(),
				course.getDescription(),
				courseSchedule.getInstructor(),
				course.getLength(),
				course.getMode(),
				precioFormateado,
				courseSchedule.getSede().getName(),
				courseSchedule.getSede().getAddress(),
				"Día " + courseSchedule.getDiaEnQueSeDicta(),
				courseSchedule.getHoraInicio(),
				courseSchedule.getHoraFin()
			);
			emailService.sendEmail(student.getEmail(), subject, body);
		} catch (Exception e) {
			System.err.println("Error al enviar email de confirmación para inscripción ID: " + savedInscripcion.getId() + " - " + e.getMessage());
		}

		// 7. DEVOLVER RESPUESTA
		return new InscripcionExitosaDTO(
				savedInscripcion.getId(),
				savedInscripcion.getCourseSchedule().getCourse().getName(),
				savedInscripcion.getStudent().getFirstName(),
				savedInscripcion.getStudent().getEmail(),
				savedInscripcion.getFechaInscripcion(),
				savedInscripcion.getEstado()
		);
	}
    
	
	@Transactional
	public ResultadoCancelacionDTO cancelarInscripcion(CancelacionDTO dto) {
		// PASO 1: Búsqueda y validaciones iniciales (sin cambios)
		Inscripcion inscripcion = inscripcionRepository.findById(dto.getIdInscripcion())
				.orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada con ID: " + dto.getIdInscripcion()));

		if ("CANCELADA".equals(inscripcion.getEstado())) {
			throw new IllegalStateException("Esta inscripción ya fue cancelada previamente.");
		}

		Student student = inscripcion.getStudent();
		CourseSchedule courseSchedule = inscripcion.getCourseSchedule();
		Course course = courseSchedule.getCourse();
		
		LocalDate fechaCancelacion = LocalDate.now();
		LocalDate fechaInicioCurso = course.getFechaInicio();

		if (fechaCancelacion.isAfter(fechaInicioCurso)) {
			throw new IllegalStateException("No se puede cancelar una vez iniciado el curso. No corresponde reintegro.");
		}

		// PASO 2: Calcular monto y porcentaje a reintegrar (sin aplicar el reintegro aún)
		long diasHabilesAntes = contarDiasHabiles(fechaCancelacion, fechaInicioCurso);
		double montoReintegrado = 0;
		String porcentajeMensaje = "";

		if (diasHabilesAntes >= 10) {
			montoReintegrado = course.getPrice();
			porcentajeMensaje = "100%";
		} else if (diasHabilesAntes >= 1 && diasHabilesAntes <= 9) {
			montoReintegrado = course.getPrice() * 0.70;
			porcentajeMensaje = "70%";
		} else { // Mismo día de inicio
			montoReintegrado = course.getPrice() * 0.50;
			porcentajeMensaje = "50%";
		}

		// --- LÓGICA DE REINTEGRO REESTRUCTURADA ---
		// PASO 3: Aplicar el reintegro según la elección del usuario
		
		// Validamos que el usuario haya enviado una opción de reintegro
		if (dto.getTipoReintegro() == null) {
			throw new IllegalArgumentException("Debe seleccionar un método de reintegro (TARJETA o CUENTA_CORRIENTE).");
		}

		String mensajeFinal = String.format("Cancelación procesada. Se reintegró el %s ($%.2f)", porcentajeMensaje, montoReintegrado);

		if (dto.getTipoReintegro() == TipoReintegro.CUENTA_CORRIENTE) {
			student.setCuentaCorriente(student.getCuentaCorriente() + montoReintegrado);
			studentRepository.save(student);
			mensajeFinal += " a tu cuenta corriente.";
		} else { // tipoReintegro == TARJETA
			// Lógica de reintegro a tarjeta (simulado)
			mensajeFinal += ", el reintegro se procesará a tu tarjeta de crédito.";
		}

		// PASO 4: Actualizar el estado de la inscripción y las vacantes (sin cambios)
		inscripcion.setEstado("CANCELADA");
		inscripcionRepository.save(inscripcion);

		courseSchedule.setVacancy(courseSchedule.getVacancy() + 1);
		courseSchedRepository.save(courseSchedule);

		return new ResultadoCancelacionDTO(mensajeFinal, montoReintegrado, "CANCELADA");
	}

	// --- MÉTODO AUXILIAR PARA CONTAR DÍAS HÁBILES ---
	// Podés ponerlo al final de tu clase Controlador.java
	private long contarDiasHabiles(LocalDate fechaInicio, LocalDate fechaFin) {
		if (fechaInicio.isAfter(fechaFin)) {
			return 0;
		}
		long diasHabiles = 0;
		LocalDate diaActual = fechaInicio;
		while (!diaActual.isAfter(fechaFin)) {
			DayOfWeek dia = diaActual.getDayOfWeek();
			if (dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY) {
				diasHabiles++;
			}
			diaActual = diaActual.plusDays(1);
		}
		// No contamos el día de inicio del curso en el cálculo de "días antes".
		// Si cancela el mismo día, el resultado debe ser 0.
		return diasHabiles > 0 ? diasHabiles - 1 : 0;
	}
    
    public List<InscripcionView> findByStudent(Long studentId) {
        return inscripcionRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToView)
                .collect(Collectors.toList());
    }

	private InscripcionView mapToView(Inscripcion inscripcion) {
        return new InscripcionView(
                inscripcion.getId(),
                inscripcion.getStudent(),
                inscripcion.getCourseSchedule(),
                inscripcion.getFechaInscripcion(),
                inscripcion.getEstado(),
                inscripcion.getAsistencias()
        );
    }


	//-----------------------------------------------CourseAttended--------------------------------------------------------------------------------------------------------------------------------------------------------
	@Transactional
	public CourseAttended registrarAsistencia(AsistenciaDTO dto) {
		// 1. Buscamos la inscripción activa del alumno para ese cronograma
		Inscripcion inscripcion = inscripcionRepository
				.findByStudentIdAndCourseScheduleIdAndEstado(dto.getIdAlumno(), dto.getIdCronograma(), "ACTIVA")
				.orElseThrow(() -> new IllegalArgumentException("No se encontró una inscripción activa para este alumno y curso."));

		LocalDate hoy = LocalDate.now();

		// 2. Verificamos que no haya registrado asistencia hoy para esta inscripción
		boolean yaAsistioHoy = courseAttendRepository
								.existsByInscripcionAndFechaAsistencia(inscripcion, hoy);

		if (yaAsistioHoy) {
			throw new IllegalStateException("El alumno ya registró su asistencia para el día de hoy.");
		}

		// 3. Creamos el nuevo registro de asistencia
		CourseAttended nuevaAsistencia = CourseAttended.builder()
				.inscripcion(inscripcion)
				.fechaAsistencia(hoy)
				.presente(true)
				.build();

		return courseAttendRepository.save(nuevaAsistencia);
	}

	public AsistenciaResultadoDTO verificarAsistencia(Long idInscripcion) {
		// 1. Buscamos la inscripción
		Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion)
				.orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada con ID: " + idInscripcion));

		// 2. Contamos las clases a las que asistió
		long clasesAsistidas = courseAttendRepository.countByInscripcion(inscripcion);

		// 3. Calculamos el número total de clases del curso
		int totalClases = contarClasesTotales(inscripcion.getCourseSchedule());

		if (totalClases == 0) {
			return new AsistenciaResultadoDTO(0, 0, 0, false, "El curso no tiene clases programadas.");
		}

		// 4. Calculamos el porcentaje
		double porcentajeAsistencia = ((double) clasesAsistidas / totalClases) * 100.0;

		// 5. Determinamos si aprobó
		boolean aprobado = porcentajeAsistencia >= 75.0;
		String mensaje = aprobado ? "¡Felicidades! Aprobaste el curso." : "No se alcanzó el 75% de asistencia requerido para aprobar.";

		return new AsistenciaResultadoDTO(totalClases, clasesAsistidas, porcentajeAsistencia, aprobado, mensaje);
	}

	// Método auxiliar para contar el total de clases
	private int contarClasesTotales(CourseSchedule cronograma) {
		LocalDate fechaInicio = cronograma.getCourse().getFechaInicio();
		LocalDate fechaFin = cronograma.getCourse().getFechaFin();
		DayOfWeek diaDeClase = DayOfWeek.of(cronograma.getDiaEnQueSeDicta());

		int totalClases = 0;
		LocalDate diaActual = fechaInicio;

		while (!diaActual.isAfter(fechaFin)) {
			if (diaActual.getDayOfWeek() == diaDeClase) {
				totalClases++;
			}
			// CAMBIO CLAVE: Avanzamos día por día en lugar de semana por semana
			diaActual = diaActual.plusDays(1);
		}
		return totalClases;
	}

}