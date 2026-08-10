# --- ECR: repositorio privado para la imagen de la API ---
resource "aws_ecr_repository" "api" {
  name                 = "franquicias-api"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

# --- Rol de ejecución: permite a ECS bajar la imagen de ECR y mandar logs a CloudWatch ---
resource "aws_iam_role" "ecs_execution" {
  name = "franquicias-ecs-execution-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect    = "Allow"
      Principal = { Service = "ecs-tasks.amazonaws.com" }
      Action    = "sts:AssumeRole"
    }]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_execution_managed" {
  role       = aws_iam_role.ecs_execution.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

# --- Rol de infraestructura: permite a ECS crear/gestionar el ALB, security groups y TLS de Express Mode ---
resource "aws_iam_role" "ecs_infrastructure" {
  name = "franquicias-ecs-infrastructure-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Sid       = "AllowAccessToECSForInfrastructureManagement"
      Effect    = "Allow"
      Principal = { Service = "ecs.amazonaws.com" }
      Action    = "sts:AssumeRole"
    }]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_infrastructure_managed" {
  role       = aws_iam_role.ecs_infrastructure.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSInfrastructureRoleforExpressGatewayServices"
}

# --- CloudWatch: logs de la app ---
resource "aws_cloudwatch_log_group" "api" {
  name              = "/ecs/franquicias-api"
  retention_in_days = 7
}

# --- Connection string completo de Atlas, construido a partir del cluster que ya provisionamos ---
locals {
  atlas_host       = replace(mongodbatlas_advanced_cluster.this.connection_strings.standard_srv, "mongodb+srv://", "")
  mongodb_full_uri = "mongodb+srv://${var.db_username}:${var.db_password}@${local.atlas_host}/franquicias?retryWrites=true&w=majority"
}

# --- El servicio en sí: ECS Express Mode ---
resource "aws_ecs_express_gateway_service" "api" {
  service_name             = "franquicias-api"
  execution_role_arn       = aws_iam_role.ecs_execution.arn
  infrastructure_role_arn  = aws_iam_role.ecs_infrastructure.arn
  cpu                      = "512"
  memory                   = "1024"
  health_check_path        = "/actuator/health"

  primary_container {
    image          = "${aws_ecr_repository.api.repository_url}:${var.docker_image_tag}"
    container_port = 8080

    aws_logs_configuration {
      log_group         = aws_cloudwatch_log_group.api.name
      log_stream_prefix = "ecs"
    }

    environment {
      name  = "SPRING_MONGODB_URI"
      value = local.mongodb_full_uri
    }
  }

  depends_on = [
    aws_iam_role_policy_attachment.ecs_execution_managed,
    aws_iam_role_policy_attachment.ecs_infrastructure_managed,
  ]
}