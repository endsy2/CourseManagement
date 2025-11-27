# API Gateway Migration: WebFlux → MVC

## Changes Made

### 1. Dependencies (build.gradle)
- ✅ Already using `spring-boot-starter-web` (MVC)
- ✅ Removed WebFlux and Gateway dependencies

### 2. Security Configuration
**New:** `SecurityConfiguration.java`
- Uses standard Spring Security with `HttpSecurity`
- JWT filter added before `UsernamePasswordAuthenticationFilter`
- Stateless session management
- Public endpoints: `/user-service/auth/**`

### 3. JWT Authentication Filter
**Updated:** `JwtAuthenticationFilter.java`
- Changed from `GlobalFilter` (WebFlux) to `OncePerRequestFilter` (MVC)
- Uses servlet `HttpServletRequest` and `HttpServletResponse`
- Fixed imports (jakarta.servlet instead of javax.servlet)

### 4. CORS Configuration
**New:** `CorsConfiguration.java`
- Uses `CorsFilter` instead of `CorsWebFilter`
- Allows all origins, methods, and headers

### 5. Proxy Controller
**New:** `ProxyController.java`
- Replaces Spring Cloud Gateway routing
- Routes:
  - `/user-service/**` → `http://localhost:8080`
  - `/course-service/**` → `http://localhost:8090`
- Uses `RestTemplate` to forward requests
- Preserves headers and request body

### 6. Deleted Files
- ❌ `SecurityFilterChain.java` (old)
- ❌ `GlobalCorsConfig.java` (WebFlux)
- ❌ `GatewayConfig.java` (WebFlux)
- ❌ `controller.java` (test file)

## How to Test

1. **Rebuild the project:**
   ```bash
   cd api-gateway
   ./gradlew clean build
   ```

2. **Start the gateway:**
   ```bash
   ./gradlew bootRun
   ```

3. **Login to get JWT token:**
   ```http
   POST http://localhost:8654/user-service/auth/login
   Content-Type: application/json

   {
     "username": "kongming",
     "password": "your_password"
   }
   ```

4. **Test protected endpoint:**
   ```http
   GET http://localhost:8654/user-service/test
   Authorization: Bearer YOUR_TOKEN_HERE
   ```

## Key Differences: WebFlux vs MVC

| Feature | WebFlux (Old) | MVC (New) |
|---------|---------------|-----------|
| Programming Model | Reactive (Mono/Flux) | Blocking/Servlet |
| Filter Base Class | `GlobalFilter` | `OncePerRequestFilter` |
| Request/Response | `ServerWebExchange` | `HttpServletRequest/Response` |
| Security Context | `ReactiveSecurityContextHolder` | `SecurityContextHolder` |
| Routing | Spring Cloud Gateway | Controller + RestTemplate |

## Notes

- The gateway now uses blocking I/O instead of reactive
- Better for simple proxy use cases
- Easier to debug and understand
- Lower memory footprint for low-traffic scenarios
