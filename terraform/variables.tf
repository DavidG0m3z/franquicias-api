variable "atlas_org_id" {
  description = "Organization ID de MongoDB Atlas"
  type        = string
}

variable "db_username" {
  description = "Usuario de la base de datos para la aplicación"
  type        = string
  default     = "franquicias_app"
}

variable "db_password" {
  description = "Password del usuario de la base de datos"
  type        = string
  sensitive   = true
}

variable "docker_image_tag" {
  description = "Tag de la imagen en ECR a desplegar"
  type        = string
  default     = "latest"
}