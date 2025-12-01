# Music Player (Frontend + Spring Boot Backend)

Projeto composto por:
- Frontend React + Vite + Tailwind (TypeScript)
- Backend Spring Boot gerenciando playlist via lista encadeada

## Sumário
1. Visão Geral
2. Arquitetura
3. Estrutura de Pastas
4. Backend: Estrutura de Dados e APIs
5. Frontend: Fluxo e Componentes
6. Variáveis de Ambiente
7. Execução (Dev & Build)
8. Funcionamento Interno
9. Endpoints
10. Supabase
11. Licença / Observações

---

## 1. Visão Geral

O sistema carrega automaticamente arquivos `.mp3` empacotados no backend (classpath `static/audios`), organiza-os em uma lista encadeada, expõe operações de navegação (próxima, anterior, aleatória) e disponibiliza uma API REST simples consumida pelo frontend. O frontend oferece interface moderna de player com playlist, controle de reprodução e seleção de faixas.

---

## 2. Arquitetura

Fluxo principal:
1. Backend inicia e varre `classpath:static/audios/*.mp3`.
2. Cada arquivo vira objeto de domínio [`org.estrutura.dados.model.Musica`](backend/src/main/java/org/estrutura/dados/model/Musica.java).
3. Objetos são inseridos em lista encadeada através de [`org.estrutura.dados.structures.ListaEncadeada`](backend/src/main/java/org/estrutura/dados/structures/ListaEncadeada.java).
4. Serviço [`org.estrutura.dados.service.MusicService`](backend/src/main/java/org/estrutura/dados/service/MusicService.java) mantém estado da música atual.
5. Controlador REST [`org.estrutura.dados.controller.MusicaController`](backend/src/main/java/org/estrutura/dados/controller/MusicaController.java) expõe endpoints.
6. Frontend inicia (`npm run dev`), chama `/api/playlist`, depois sincroniza `/api/current`.
7. Ao trocar faixa, frontend envia POST para `/api/play/{id}` e atualiza estado local.

---

## 3. Estrutura de Pastas

```
backend/
  src/main/java/org/estrutura/dados/
    A3Application.java
    controller/
    service/
    model/
    structures/
frontend/
  src/
    App.tsx
    components/
    lib/
    index.css
    main.tsx
```

---

## 4. Backend: Estrutura de Dados e APIs

### Lista Encadeada
Implementada em [`org.estrutura.dados.structures.ListaEncadeada`](backend/src/main/java/org/estrutura/dados/structures/ListaEncadeada.java). Cada nó: [`org.estrutura.dados.structures.No`](backend/src/main/java/org/estrutura/dados/structures/No.java).

Operações:
- Adicionar
- Remover por ID
- Buscar por ID
- Próxima / anterior (wrap-around)
- Aleatória (`ThreadLocalRandom`)

### Serviço
[`org.estrutura.dados.service.MusicService`](backend/src/main/java/org/estrutura/dados/service/MusicService.java):
- Carrega `.mp3` de `classpath:static/audios`
- Mantém `currentId`
- Navegação e seleção

### Controlador
[`org.estrutura.dados.controller.MusicaController`](backend/src/main/java/org/estrutura/dados/controller/MusicaController.java) expõe endpoints REST.

### Bootstrapping
[`org.estrutura.dados.A3Application`](backend/src/main/java/org/estrutura/dados/A3Application.java).

---

## 5. Frontend: Fluxo e Componentes

- Entrada: [`src/main.tsx`](frontend/src/main.tsx)
- Raiz: [`src/App.tsx`](frontend/src/App.tsx)
- Player: [`src/components/MusicPlayer.tsx`](frontend/src/components/MusicPlayer.tsx)
- Lista: [`src/components/TrackList.tsx`](frontend/src/components/TrackList.tsx)
- Supabase (futuro): [`src/lib/supabase.ts`](frontend/src/lib/supabase.ts)

---

## 6. Variáveis de Ambiente

[`frontend/.env`](frontend/.env):
- `VITE_SUPABASE_URL`
- `VITE_SUPABASE_ANON_KEY`
- `VITE_BACKEND_URL`

---

## 7. Execução (Dev & Build)

### Backend (Spring Boot + Maven)

Arquivo de configuração: [`backend/pom.xml`](backend/pom.xml)

Desenvolvimento (hot reload):
```sh
cd backend
mvn spring-boot:run
```

Build padrão (gera JAR simples):
```sh
cd backend
mvn clean package
```
Resultado: `backend/target/A3EstruturaDeDados-1.0.jar`

Execução do JAR gerado (requer dependências no classpath; sem plugin ainda não é fat-jar):
```sh
java -jar target/A3EstruturaDeDados-1.0.jar
```

Para empacotar como JAR executável com dependências, adicionar o plugin Spring Boot:

```xml
<!-- Adicionar dentro de <project> em [backend/pom.xml](backend/pom.xml) -->
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <version>3.5.6</version>
    </plugin>
  </plugins>
</build>
```

Após isso:
```sh
mvn clean package
java -jar target/A3EstruturaDeDados-1.0.jar
```

API disponível: `http://localhost:8080/api/*`  
Arquivos de áudio: `http://localhost:8080/audios/{nomeArquivo}`

### Frontend

```sh
cd frontend
npm install
npm run dev     # http://localhost:5173
npm run build   # build produção em dist/
npm run preview
npm run typecheck
```

---

## 8. Funcionamento Interno

Conversão de duração:
$mins = \lfloor seconds / 60 \rfloor,\ secs = seconds \bmod 60$

Random: seleção uniforme sobre a lista.

---

## 9. Endpoints

Base `/api` via [`org.estrutura.dados.controller.MusicaController`](backend/src/main/java/org/estrutura/dados/controller/MusicaController.java):

GET `/playlist`  
GET `/current`  
POST `/playlist`  
POST `/next`  
POST `/previous`  
POST `/play/{id}`  
DELETE `/playlist/{id}`  
POST `/random`

Modelo: [`org.estrutura.dados.model.Musica`](backend/src/main/java/org/estrutura/dados/model/Musica.java)

---

## 10. Supabase

Migração: [`frontend/supabase/migrations/20251126030334_create_tracks_table.sql`](frontend/supabase/migrations/20251126030334_create_tracks_table.sql)

---


---

## 11. Licença / Observações

Gerenciar chaves e direitos dos áudios.

---

## Referências

Backend: [`org.estrutura.dados.A3Application`](backend/src/main/java/org/estrutura/dados/A3Application.java), [`org.estrutura.dados.service.MusicService`](backend/src/main/java/org/estrutura/dados/service/MusicService.java), [`org.estrutura.dados.structures.ListaEncadeada`](backend/src/main/java/org/estrutura/dados/structures/ListaEncadeada.java)  
Frontend: [`src/App.tsx`](frontend/src/App.tsx), [`src/components/MusicPlayer.tsx`](frontend/src/components/MusicPlayer.tsx), [`src/components/TrackList.tsx`](frontend/src/components/TrackList.tsx)

---

## Desenvolvido por:

| Nome                                | R.A         |
|-------------------------------------|-------------|
| Guilherme P Vale                    | 1252327540  |
| Leonardo Ramos dos Santos           | 1252313846  |
| Luan Gonçalves Spera                | 1252322579  |
| Matheus Vinicius Inagaki Moraes     | 12523220249 |
| Yam Mombeli de Carvalho             | 1252328669  |


