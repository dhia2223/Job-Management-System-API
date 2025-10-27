# Job Management System API

## Authentication Endpoints
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

## Job Endpoints (Require Authentication)
- `GET /api/jobs` - Get all jobs
- `GET /api/jobs/{id}` - Get job by ID
- `POST /api/jobs` - Create new job
- `PUT /api/jobs/{id}` - Update job (partial updates supported)
- `DELETE /api/jobs/{id}` - Delete job

## Search Endpoints
- `GET /api/jobs/search/company?company=TechCorp`
- `GET /api/jobs/search/location?location=Remote`
- `GET /api/jobs/search/title?title=Developer`
- `GET /api/jobs/search/job-type?jobType=FULL_TIME`

## Statistics
- `GET /api/jobs/statistics/count` - Get job counts by type
- `GET /api/jobs/statistics/company/{company}` - Company-specific stats