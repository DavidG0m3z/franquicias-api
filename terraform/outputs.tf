output "connection_string" {
  description = "Connection string SRV para spring.mongodb.uri"
  value       = mongodbatlas_advanced_cluster.this.connection_strings.standard_srv
  sensitive   = true
}

output "ecr_repository_url" {
  description = "URL del repo ECR (usar para docker tag/push)"
  value       = aws_ecr_repository.api.repository_url
}

output "api_ingress_paths" {
  description = "Info de acceso público del servicio (incluye la URL generada por Express Mode)"
  value       = aws_ecs_express_gateway_service.api.ingress_paths
}