output "connection_string" {
  description = "Connection string SRV para spring.mongodb.uri"
  value       = mongodbatlas_advanced_cluster.this.connection_strings.standard_srv
  sensitive   = true
}