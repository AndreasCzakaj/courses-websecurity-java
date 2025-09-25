# Vue.js SPA Security Demo

A Vue.js Single Page Application demonstrating secure client-side handling of user-generated content with TypeScript, Vuetify, and Vite.

## 🛡️ Security Features

- **XSS Protection**: Vue.js templates automatically escape HTML content
- **Type Safety**: TypeScript prevents type-related vulnerabilities
- **Secure HTTP**: Axios with proper error handling and request validation
- **Input Validation**: Client-side validation with Vuetify forms
- **Safe DOM Manipulation**: No direct innerHTML usage

## 🚀 Tech Stack

- **Vue 3** - Progressive JavaScript framework
- **TypeScript** - Type-safe development
- **Vuetify 3** - Material Design component library
- **Vue Router** - Client-side routing
- **Axios** - HTTP client with interceptors
- **Vite** - Fast build tool
- **Vitest** - Unit testing framework

## 📦 Installation

```bash
npm install
```

## 🏃‍♂️ Development

Start the development server:

```bash
npm run dev
```

The app will be available at [http://localhost:3000](http://localhost:3000)

## 🧪 Testing

Run unit tests:

```bash
npm run test
```

Run tests with UI:

```bash
npm run test:ui
```

Run tests with coverage:

```bash
npm run test:coverage
```

## 🏗️ Build

Build for production:

```bash
npm run build
```

Preview production build:

```bash
npm run preview
```

## 📁 Project Structure

```
spa-vue/
├── src/
│   ├── components/          # Reusable Vue components
│   ├── views/              # Page components
│   ├── router/             # Vue Router configuration
│   ├── services/           # API services
│   ├── types/              # TypeScript type definitions
│   └── plugins/            # Vue plugins configuration
├── tests/                  # Unit tests
└── public/                # Static assets
```

## 🔗 API Integration

The SPA connects to the Spring Boot backend API running on port 8080:

- `GET /api/comments` - Fetch all comments
- `POST /api/comments` - Create new comment
- `DELETE /api/comments/{id}` - Delete comment

Vite dev server automatically proxies `/api` requests to `http://localhost:8080`.

## 🛡️ Security Demo

The Comments page demonstrates:

1. **Safe Content Rendering**: All user input is automatically escaped by Vue templates
2. **Visual Security Indicators**: Comments containing potentially malicious content are flagged
3. **Input Validation**: Form validation prevents empty submissions
4. **Error Handling**: Graceful handling of API errors

Try entering malicious content like `<script>alert('XSS')</script>` - it will be safely displayed as text!